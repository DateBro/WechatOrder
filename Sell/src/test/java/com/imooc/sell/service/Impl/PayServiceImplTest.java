package com.imooc.sell.service.Impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author DateBro
 * @Date 2020/12/22 15:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
    }

    // 测试失败，有点问题
    // java.lang.UnsupportedOperationException:
    // clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+
    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1608623259962456186");
        payService.refund(orderDTO);
    }
}