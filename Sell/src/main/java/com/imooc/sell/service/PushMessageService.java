package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * @Author DateBro
 * @Date 2020/12/23 20:35
 */
public interface PushMessageService {

    void OrderStatusUpdateMessage(OrderDTO orderDTO);
}
