package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals("热销榜", productCategory.getCategoryName());
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("男生最爱", 2);
        ProductCategory result = categoryService.save(category);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> categoryTypeList = new ArrayList<>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0, categoryList.size());
    }

    @Test
    public void findAll() {
        List<ProductCategory> categoryList = categoryService.findAll();
        Assert.assertNotEquals(0, categoryList.size());
    }
}