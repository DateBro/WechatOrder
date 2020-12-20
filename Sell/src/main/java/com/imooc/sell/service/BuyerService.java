package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * @Author DateBro
 * @Date 2020/12/20 20:08
 */
public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
