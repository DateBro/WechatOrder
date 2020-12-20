package com.imooc.sell.dto;

import lombok.Data;

/**
 * @Author DateBro
 * @Date 2020/12/20 13:43
 * DTO，data transfer object，用来各层之间传输数据
 * CartDTO的属性可以参照api文档，文档中前端传来的数据有items数组，里面单个元素是productId和quantity
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
