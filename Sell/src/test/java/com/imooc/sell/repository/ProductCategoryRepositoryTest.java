package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> typeList = new ArrayList<>();
        typeList.add(1);
        List<ProductCategory> resultList = repository.findByCategoryTypeIn(typeList);
        Assert.assertNotEquals(0, resultList.size());
    }

    @Test
    public void findOne() {
        ProductCategory productCategory = repository.findOne(1);
        log.info("【ProductCategoryRepository测试】findOne结果为", productCategory);
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(1);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotEquals(null, result);
    }
}