package com.imooc.sell.service.Impl;

import com.imooc.sell.converter.OrderDetail2CartDTOConverter;
import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.WebSocketService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 设置orderId
        String orderId = KeyUtil.genUniqueKey();

        // 计算orderAmount，这个前端不会传也不能传
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (OrderDetail detail : orderDTO.getDetailList()) {
            String productId = detail.getProductId();
            int productQuantity = detail.getProductQuantity();

            ProductInfo productInfo = productService.findOne(productId);
            // 这里要记得处理如果查询不到商品的问题
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST_ERROR);
            }
            BigDecimal productPrice = productInfo.getProductPrice();
            orderAmount = orderAmount.add(productPrice.multiply(new BigDecimal(productQuantity)));

            // 保存订单详情
            detail.setDetailId(KeyUtil.genUniqueKey());
            detail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, detail);
            detailRepository.save(detail);

            CartDTO cartDTO = new CartDTO(productId, productQuantity);
            cartDTOList.add(cartDTO);
        }

        // 设置订单属性并保存订单
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);

        // 记得减库存
        productService.decreaseStock(cartDTOList);

        // 通知买家订单状态
//        orderDTO.setOrderAmount(orderAmount);
//        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
//        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
//        pushMessageService.OrderStatusUpdateMessage(orderDTO);

        // 通知卖家有新订单
        log.info("进入通知卖家有新订单");
        webSocketService.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = masterRepository.findOne(orderId);
        if(orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST_ERROR);
        }
        List<OrderDetail> detailList = detailRepository.findByOrderId(orderId);
        if (detailList.isEmpty()) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST_ERROR);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setDetailList(detailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masterPage = masterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(masterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, masterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> masterPage = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(masterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, masterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // 查看订单状态有无问题，只有新下单的订单可以取消
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态有问题");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster cancelResult = masterRepository.save(orderMaster);
        if (cancelResult == null) {
            log.error("【取消订单】修改订单状态出错");
            throw new SellException(ResultEnum.ORDER_STATUS_MODIFY_FAIL);
        }
        // 增加库存
        List<OrderDetail> orderDetailList = orderDTO.getDetailList();
        if (orderDetailList.isEmpty()) {
            log.error("【取消订单】订单详情列表为空");
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST_ERROR);
        }
        List<CartDTO> cartDTOList = OrderDetail2CartDTOConverter.convert(orderDetailList);
        productService.increaseStock(cartDTOList);

        // 如果已支付，需要退款，这里实现暂时有点问题
        // TODO
//        if (orderDTO.getPayStatus().equals(PayStatusEnum.PAID.getCode())) {
//            payService.refund(orderDTO);
//        }

        // 通知买家订单状态
//        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
//        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
//        pushMessageService.OrderStatusUpdateMessage(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // 查看订单状态有无问题，只有新下单的订单可以完结
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态有问题");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster finishResult = masterRepository.save(orderMaster);
        if (finishResult == null) {
            log.error("【完结订单】修改订单状态出错");
            throw new SellException(ResultEnum.ORDER_STATUS_MODIFY_FAIL);
        }

        // 通知买家订单状态
//        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
//        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
//        pushMessageService.OrderStatusUpdateMessage(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // 查看订单状态有无问题，只有新下单的订单可以支付
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态有问题");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 查看订单支付状态有无问题，只有未支付的订单可以支付
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】支付状态有问题");
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }

        // 修改支付状态
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【支付订单】支付状态更新有问题");
            throw new SellException(ResultEnum.PAY_STATUS_MODIFY_FAIL);
        }

        return orderDTO;
    }
}
