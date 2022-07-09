package com.gxdxx.shop.dto;

import com.gxdxx.shop.constant.CouponType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponListDto {

    private Long id;

    private String couponName;

    private Integer discount;

    private String couponDescription;

    private CouponType couponType;

    private LocalDateTime registerTime;

    @QueryProjection
    public CouponListDto(Long id, String couponName, int discount, String couponDescription, CouponType couponType, LocalDateTime registerTime) {
        this.id = id;
        this.couponName = couponName;
        this.discount = discount;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
        this.registerTime = registerTime;
    }

}