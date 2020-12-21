package com.imooc.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author DateBro
 * @Date 2020/12/21 16:12
 */
@Component
public class WechatMpConfig {

    @Autowired
    WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        // 再对wxMpService进行配置
        wxMpService.setWxMpConfigStorage(wxMpDefaultConfig());
        return wxMpService;
    }

    @Bean
    public WxMpDefaultConfigImpl wxMpDefaultConfig() {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(accountConfig.getMpAppId());
        wxMpDefaultConfig.setSecret(accountConfig.getMpAppSecret());
        return wxMpDefaultConfig;
    }
}
