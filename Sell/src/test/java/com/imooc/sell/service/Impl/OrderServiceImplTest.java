package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author DateBro
 * @Date 2020/12/20 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl service;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        String orderId = KeyUtil.genUniqueKey();
        orderDTO.setOrderId(orderId);
        orderDTO.setBuyerName("DateBro");
        orderDTO.setBuyerPhone("18512345678");
        orderDTO.setBuyerAddress("历下区舜华路");
        orderDTO.setBuyerOpenid("myopenid");
        List<OrderDetail> detailList = new ArrayList<>();

        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId("123");
        detail1.setProductQuantity(4);
        detailList.add(detail1);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId("123456");
        detail2.setProductQuantity(2);
        detailList.add(detail2);

        orderDTO.setDetailList(detailList);

        OrderDTO result = service.create(orderDTO);
        log.info("【创建订单】result = {}", result);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void pay() {
    }
}