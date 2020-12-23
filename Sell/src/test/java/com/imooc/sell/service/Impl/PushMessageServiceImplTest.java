package com.imooc.sell.service.Impl;

import com.imooc.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author DateBro
 * @Date 2020/12/23 20:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void orderStatusUpdateMessage() {
        OrderDTO orderDTO = orderService.findOne("1608619774706343459");
        pushMessageService.OrderStatusUpdateMessage(orderDTO);
    }
}