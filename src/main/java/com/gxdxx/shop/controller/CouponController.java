package com.gxdxx.shop.controller;

import com.gxdxx.shop.dto.CouponFormDto;
import com.gxdxx.shop.dto.CouponListDto;
import com.gxdxx.shop.dto.CouponSearchDto;
import com.gxdxx.shop.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping(value = "/admin/coupon/new")
    public String couponForm(Model model) {
        model.addAttribute("couponFormDto", new CouponFormDto());
        return "coupon/couponForm";
    }

    @PostMapping(value = "/admin/coupon/new")
    public String couponNew(@Valid CouponFormDto couponFormDto, BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "coupon/couponForm";
        }

        try {
            couponService.saveCoupon(couponFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "쿠폰 등록 중 에러가 발생했습니다.");
            return "coupon/couponForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/coupon/{couponId}")
    public String couponDtl(@PathVariable("couponId") Long couponId, Model model) {

        CouponFormDto couponFormDto = couponService.getCouponDtl(couponId);
        model.addAttribute("couponFormDto", couponFormDto);

        return"coupon/couponForm";
    }

    @PostMapping(value = "/admin/coupon/{couponId}")
    public String couponUpdate(@Valid CouponFormDto couponFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "coupon/couponForm";
        }

        try {
            couponService.updateCoupon(couponFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "쿠폰 수정 중 에러가 발생했습니다.");
            return "coupon/couponForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/coupons", "/admin/coupons/{page}"})
    public String couponManage(CouponSearchDto couponSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<CouponListDto> coupons = couponService.getAdminCouponPage(couponSearchDto, pageable);

        model.addAttribute("coupons", coupons);
        model.addAttribute("couponSearchDto", couponSearchDto);
        model.addAttribute("maxPage", 5);

        return "coupon/couponManagement";
    }

    @GetMapping(value = "/coupon/{couponId}")
    public String couponDtl(Model model, @PathVariable("couponId") Long couponId) {
        CouponFormDto couponFormDto = couponService.getCouponDtl(couponId);
        model.addAttribute("coupon", couponFormDto);
        return "coupon/couponDtl";
    }

}