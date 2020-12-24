package com.imooc.sell.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 8874085647279084088L;
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
