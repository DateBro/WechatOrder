package com.imooc.sell.service.Impl;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author DateBro
 * @Date 2020/12/23 20:35
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void OrderStatusUpdateMessage(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(orderDTO.getBuyerOpenid())
                .templateId(accountConfig.getTemplateId().get("orderStatus"))
                .build();
        templateMessage.addData(new WxMpTemplateData("first", "亲，请记得收货。"));
        templateMessage.addData(new WxMpTemplateData("keyword1", "微信点餐"));
        templateMessage.addData(new WxMpTemplateData("keyword2", "12345678123"));
        templateMessage.addData(new WxMpTemplateData("keyword3", orderDTO.getOrderId()));
        templateMessage.addData(new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()));
        templateMessage.addData(new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()));
        templateMessage.addData(new WxMpTemplateData("remark", "欢迎再次光临！"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】订单状态更新信息发送失败");
        }
    }
}
