package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        String productId = "123456";
        ProductInfo result = productService.findOne(productId);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> results = productService.findUpAll();
        Assert.assertNotEquals(0, results.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("土豆丝");
        productInfo.setProductPrice(new BigDecimal(2.0));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("超意兴特色酸辣土豆丝");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotEquals(null, result);
    }
}