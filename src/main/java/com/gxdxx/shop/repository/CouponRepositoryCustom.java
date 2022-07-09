package com.gxdxx.shop.repository;

import com.gxdxx.shop.dto.CouponListDto;
import com.gxdxx.shop.dto.CouponSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepositoryCustom {

    Page<CouponListDto> getAdminCouponPage(CouponSearchDto couponSearchDto, Pageable pageable);

}
