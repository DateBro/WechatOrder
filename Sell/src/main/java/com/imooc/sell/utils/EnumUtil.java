package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @Author DateBro
 * @Date 2020/12/22 19:45
 * 这个感觉确实妙啊，如果不这样我可能每个需要查找status的类都写一个for遍历查询的函数了
 */
public interface EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
