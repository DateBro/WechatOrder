package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author DateBro
 * @Date 2020/12/23 16:04
 */
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    // 点餐系统
    private String sell;

    // 微信公众平台授权
    private String wechatMpAuthorize;

    // 微信开放平台授权
    private String wechatOpenAuthorize;

    // 微信授权登录需要使用廖师兄的链接转发
    private String wechatLoginProxy;
}
