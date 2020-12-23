package com.imooc.sell.service;

import com.imooc.sell.dataobject.SellerInfo;

/**
 * @Author DateBro
 * @Date 2020/12/23 14:52
 */
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
