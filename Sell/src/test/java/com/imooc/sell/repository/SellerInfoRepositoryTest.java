package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
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
 * @Date 2020/12/23 14:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void findByOpenid() {
        String openid = "myopenid";
        SellerInfo result = repository.findByOpenid(openid);
        Assert.assertNotNull(result);
    }

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setPassword("123456");
        sellerInfo.setUsername("DateBro");
        sellerInfo.setOpenid("myopenid");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotEquals(null, result);
    }
}