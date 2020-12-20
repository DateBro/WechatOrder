package com.imooc.sell.utils;

import java.util.Random;

/**
 * @Author DateBro
 * @Date 2020/12/20 13:20
 */
public class KeyUtil {

    /**生成唯一主键，通过当前时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
