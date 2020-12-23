package com.imooc.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DateBro
 * @Date 2020/12/23 13:12
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;
}
