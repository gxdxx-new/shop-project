package com.gxdxx.shop.dto;

import com.gxdxx.shop.constant.CouponType;
import com.gxdxx.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponSearchDto {

    private String searchDateType;

    private CouponType couponType;

    private String searchBy;

    private String searchQuery = "";

}