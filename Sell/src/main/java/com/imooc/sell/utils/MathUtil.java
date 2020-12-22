package com.imooc.sell.utils;

/**
 * @Author DateBro
 * @Date 2020/12/22 15:05
 */
public class MathUtil {

    private static final Double TOLERABLE_DIFF_RANGE = 0.01;

    public static Boolean equals(Double val1, Double val2) {
        if (Math.abs(val1 - val2) < TOLERABLE_DIFF_RANGE) {
            return true;
        } else {
            return false;
        }
    }
}
