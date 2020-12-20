package com.imooc.sell.service.Impl;

import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author DateBro
 * @Date 2020/12/20 20:10
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【查询订单】查询不到指定订单");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST_ERROR);
        }
        return orderDTO;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            return null;
        }
        if (!orderMaster.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】订单的openid不一致");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return OrderMaster2OrderDTOConverter.convert(orderMaster);
    }
}
