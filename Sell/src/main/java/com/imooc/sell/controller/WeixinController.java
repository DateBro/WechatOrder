package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DateBro
 * @Date 2020/12/21 13:11
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam(value = "code") String code) {
        log.info("进入auth方法");
        log.info("【获取到的openid】code = {}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=wxdb309ca9b6cab881&secret=415c3a6c2c80840c53164e7f3e2c6369&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response = {}", response);
    }
}
