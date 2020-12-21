package com.imooc.sell.controller;

import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @Author DateBro
 * @Date 2020/12/21 15:56
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam(value = "returnUrl", required = true) String returnUrl) {
        log.info("进入authorize方法");
        String url = "http://datebrosell.natapp1.cc/sell/wechat/userInfo";
        /**
         * build的三个参数分别是redirect_uri,scope,state
         * 官方文档里state没写，但视频中设为returnUrl，因为这样重定向时会带上returnUrl信息用于后续处理
         */
        String redirectedUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectedUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String returnUrl) {
        log.info("进入userInfo方法");
        // 视频中用的是WxMpOAuth2AccessToken，但好像4.0版本没有这个类了
        // 官方文档好像也没有更新这里，按照文档上其实是WxMpOAuth2AccessToken，但使用的时候没法import class
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid = wxOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}
