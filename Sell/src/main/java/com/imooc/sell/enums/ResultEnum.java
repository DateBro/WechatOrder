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
    ORDER_NOT_EXIST_ERROR(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST_ERROR(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态有问题"),
    ORDER_STATUS_MODIFY_FAIL(15, "修改订单状态失败"),
    PAY_STATUS_ERROR(16, "支付状态有问题"),
    PAY_STATUS_MODIFY_FAIL(17, "支付状态修改失败"),
    PARAM_ERROR(18, "输入参数错误"),
    CART_EMPTY_ERROR(19, "购物车为空"),
    BUYER_UNACCESSIBLE_ERROR(20, "用户无权限操作该订单"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
