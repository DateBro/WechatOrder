package com.imooc.sell.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVO {

    @JsonProperty("id")
    String productId;

    @JsonProperty("name")
    String productName;

    @JsonProperty("price")
    BigDecimal productPrice;

    @JsonProperty("description")
    String productDescription;

    @JsonProperty("icon")
    String productIcon;
}
