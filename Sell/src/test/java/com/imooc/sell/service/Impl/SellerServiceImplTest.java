package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author DateBro
 * @Date 2020/12/23 14:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl service;

    @Test
    public void findSellerInfoByOpenid() {
        String openid = "myopenid";
        SellerInfo result = service.findSellerInfoByOpenid(openid);
        Assert.assertNotNull(result);
    }
}