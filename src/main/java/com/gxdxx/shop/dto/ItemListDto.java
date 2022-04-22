package com.gxdxx.shop.dto;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemListDto {

    private Long id;

    private String itemName;

    private Integer price;

    private Integer stockQuantity;

    private ItemSellStatus itemSellStatus;

    private String createdBy;

    private LocalDateTime registerTime;

    @QueryProjection
    public ItemListDto(Long id, String itemName, Integer price, Integer stockQuantity, ItemSellStatus itemSellStatus, String createdBy, LocalDateTime registerTime) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemSellStatus = itemSellStatus;
        this.createdBy = createdBy;
        this.registerTime = registerTime;
    }
}
