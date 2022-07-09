package com.gxdxx.shop.dto;

import com.gxdxx.shop.constant.CouponType;
import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CouponFormDto {

    private Long id;

    @NotBlank(message = "쿠폰명은 필수 입력 값입니다.")
    private String couponName;

    @NotNull(message = "할인율은 필수 입력 값입니다.")
    private Integer discount;

    @NotBlank(message = "쿠폰 상세설명은 필수 입력 값입니다.")
    private String couponDescription;

    private CouponType couponType;

    @Builder
    public CouponFormDto(Long id, String couponName, int discount, String couponDescription, CouponType couponType) {
        this.id = id;
        this.couponName = couponName;
        this.discount = discount;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
    }

}