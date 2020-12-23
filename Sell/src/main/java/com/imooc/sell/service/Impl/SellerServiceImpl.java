package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.repository.SellerInfoRepository;
import com.imooc.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author DateBro
 * @Date 2020/12/23 14:52
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
