package com.imooc.sell.controller;

import com.imooc.sell.form.OrderForm;
import com.imooc.sell.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.Impl.BuyerServiceImpl;
import com.imooc.sell.service.Impl.OrderServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DateBro
 * @Date 2020/12/20 16:16
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;

    /**
     * 创建订单
     *
     * @Valid用于校验，每个@Valid后面必须跟一个BindingResult
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        // 先判断表单是否有错误
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】表单错误");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        // 从OrderForm中拿出要创建的订单数据
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        // 检查购物车是否为空
        if (CollectionUtils.isEmpty(orderDTO.getDetailList())) {
            log.error("【创建订单】购物车为空");
            throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "openid", required = true) String openid,
                                         @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", required = true, defaultValue = "10") Integer size) {
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);
        List<OrderDTO> orderDTOList = orderDTOPage.getContent();
        return ResultVOUtil.success(orderDTOList);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "openid", required = true) String openid,
                                     @RequestParam(value = "orderId", required = true) String orderId) {
        return ResultVOUtil.success(buyerService.findOrderOne(openid, orderId));
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam(value = "openid", required = true) String openid,
                                     @RequestParam(value = "orderId", required = true) String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
