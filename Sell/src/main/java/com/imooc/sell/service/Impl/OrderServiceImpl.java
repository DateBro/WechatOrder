package com.imooc.sell.service.Impl;

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
import com.imooc.sell.utils.KeyUtil;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private ProductServiceImpl productService;

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
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);

        // 记得减库存
        productService.decreaseStock(cartDTOList);

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
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }
}
