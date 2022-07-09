package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.CouponType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_id")
    private Long id;    //쿠폰 코드

    @Column(nullable = false, length = 50)
    private String couponName;    //쿠폰명

    // 할인율 또는 할인가 둘 중 하나 정할수 있도록 설정해야됨
    @Column(nullable = false)
    private int discount;  //할인

    @Lob
    @Column(nullable = false)
    private String couponDescription; //쿠폰 상세 설명

    @Enumerated(EnumType.STRING)
    private CouponType couponType;  //쿠폰 할인 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Coupon(String couponName, int discount, String couponDescription, CouponType couponType) {
        this.couponName = couponName;
        this.discount = discount;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
    }

    public void updateCoupon(String couponName, int discount, String couponDescription, CouponType couponType) {
        this.couponName = couponName;
        this.discount = discount;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
    }

}