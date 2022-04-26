package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.dto.ItemFormDto;
import com.gxdxx.shop.exception.OutOfStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@ToString
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;    //상품 코드

    @Column(nullable = false, length = 50)
    private String itemName;    //상품명

    @Column(nullable = false)
    private int price;  //가격

    @Column(nullable = false)
    private int stockQuantity;  //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDescription; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    @Builder
    public Item(String itemName, int price, int stockQuantity, String itemDescription, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemDescription = itemDescription;
        this.itemSellStatus = itemSellStatus;
    }

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockQuantity = itemFormDto.getStockQuantity();
        this.itemDescription = itemFormDto.getItemDescription();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockQuantity + ")");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

}