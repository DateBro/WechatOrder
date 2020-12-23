package com.imooc.sell.constant;

/**
 * @Author DateBro
 * @Date 2020/12/23 16:38
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    // 过期时间 2小时
    Integer EXPIRE = 7200;
}
