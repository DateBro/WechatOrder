package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;

/**
 * @Author DateBro
 * @Date 2020/12/20 13:37
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
