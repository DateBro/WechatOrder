package com.imooc.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author DateBro
 * @Date 2020/12/23 15:27
 */
@Component
public class WechatOpenConfig {

    @Autowired
    WechatAccountConfig accountConfig;

    @Bean
    public WxMpService wxOpenService() {
        WxMpService wxOpenService = new WxMpServiceImpl();
        // 再对wxOpenService进行配置
        wxOpenService.setWxMpConfigStorage(wxOpenDefaultConfig());
        return wxOpenService;
    }

    @Bean
    public WxMpDefaultConfigImpl wxOpenDefaultConfig() {
        WxMpDefaultConfigImpl wxOpenDefaultConfig = new WxMpDefaultConfigImpl();
        wxOpenDefaultConfig.setAppId(accountConfig.getOpenAppId());
        wxOpenDefaultConfig.setSecret(accountConfig.getOpenAppSecret());
        return wxOpenDefaultConfig;
    }
}
