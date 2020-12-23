package com.imooc.sell.handler;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellerAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author DateBro
 * @Date 2020/12/23 19:40
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(SellerAuthException.class)
    public ModelAndView handleSellerException() {
        return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatLoginProxy()));
    }
}
