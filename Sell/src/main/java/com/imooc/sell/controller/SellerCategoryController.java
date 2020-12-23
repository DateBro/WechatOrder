package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.service.Impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author DateBro
 * @Date 2020/12/23 13:29
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory category = categoryService.findOne(categoryId);
            map.put("category", category);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【保存类目信息】表单错误");
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error");
        }

        try {
            ProductCategory category = new ProductCategory();
            if (categoryForm.getCategoryId() != null)
                category = categoryService.findOne(categoryForm.getCategoryId());
            BeanUtils.copyProperties(categoryForm, category);
            categoryService.save(category);
        } catch (SellException e) {
            log.error("【保存类目信息】{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error");
        }


        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
