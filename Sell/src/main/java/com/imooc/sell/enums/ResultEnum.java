package com.imooc.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author DateBro
 * @Date 2020/12/20 13:38
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST_ERROR(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存有问题"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
