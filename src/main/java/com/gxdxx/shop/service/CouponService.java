package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.Coupon;
import com.gxdxx.shop.exception.CouponNotFoundException;
import com.gxdxx.shop.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public Long saveCoupon(CouponFormDto couponFormDto) {

        //쿠폰 등록
        Coupon coupon = Coupon.builder()
                .couponName(couponFormDto.getCouponName())
                .discount(couponFormDto.getDiscount())
                .couponDescription(couponFormDto.getCouponDescription())
                .couponType(couponFormDto.getCouponType())
                .build();
        couponRepository.save(coupon);

        return coupon.getId();
    }

    @Transactional(readOnly = true)
    public CouponFormDto getCouponDtl(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
        CouponFormDto couponFormDto = CouponFormDto.builder()
                .id(coupon.getId())
                .couponName(coupon.getCouponName())
                .discount(coupon.getDiscount())
                .couponDescription(coupon.getCouponDescription())
                .couponType(coupon.getCouponType())
                .build();

        return couponFormDto;
    }

    public Long updateCoupon(CouponFormDto couponFormDto) throws Exception {

        //상품 수정
        Coupon coupon = couponRepository.findById(couponFormDto.getId()).orElseThrow(CouponNotFoundException::new);
        coupon.updateCoupon(couponFormDto.getCouponName(), couponFormDto.getDiscount(), couponFormDto.getCouponDescription(), couponFormDto.getCouponType());

        return coupon.getId();
    }

    @Transactional(readOnly = true)
    public Page<CouponListDto> getAdminCouponPage(CouponSearchDto couponSearchDto, Pageable pageable) {
        return couponRepository.getAdminCouponPage(couponSearchDto, pageable);
    }

}