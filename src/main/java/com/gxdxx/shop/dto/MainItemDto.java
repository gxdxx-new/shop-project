package com.gxdxx.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {

    private Long id;

    private String itemName;

    private String itemDescription;

    private String imgUrl;

    private Integer price;

    @QueryProjection
    public MainItemDto(Long id, String itemName, String itemDescription, String imgUrl, Integer price) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}