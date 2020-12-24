package com.imooc.sell.viewobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 8886566500050452704L;

    private Integer code;

    private String msg;

    private T data;
}
