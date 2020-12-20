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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
        OrderDTO result = service.findOne("1608446073450685801");
        log.info("【查询订单】result = {}", result);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0, 5);
        Page<OrderDTO> result = service.findList("myopenid", request);
        Assert.assertNotEquals(0, result.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = service.findOne("1608446073450685801");
        OrderDTO result = service.cancel(orderDTO);
        log.info("【取消订单】result = {}", result);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = service.findOne("1608446073450685801");
        OrderDTO result = service.finish(orderDTO);
        log.info("【完结订单】result = {}", result);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = service.findOne("1608446073450685801");
        OrderDTO result = service.pay(orderDTO);
        log.info("【支付订单】result = {}", result);
        Assert.assertNotEquals(null, result);
    }
}