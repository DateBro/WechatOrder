package com.imooc.sell.converter;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.CartDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DateBro
 * @Date 2020/12/20 15:39
 */
public class OrderDetail2CartDTOConverter {

    public static CartDTO convert(OrderDetail orderDetail) {
        return new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
    }

    public static List<CartDTO> convert(List<OrderDetail> orderDetailList) {
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList)
            cartDTOList.add(convert(orderDetail));

        return cartDTOList;
    }
}
