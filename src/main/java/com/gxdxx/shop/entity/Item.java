package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter @Setter
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

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockQuantity = itemFormDto.getStockQuantity();
        this.itemDescription = itemFormDto.getItemDescription();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}