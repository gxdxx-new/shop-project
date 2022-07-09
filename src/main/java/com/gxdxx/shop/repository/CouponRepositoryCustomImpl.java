package com.gxdxx.shop.repository;

import com.gxdxx.shop.constant.CouponType;
import com.gxdxx.shop.dto.*;
import com.gxdxx.shop.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CouponRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchCouponTypeEq(CouponType searchCouponType) {
        return searchCouponType == null ? null : QCoupon.coupon.couponType.eq(searchCouponType);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QCoupon.coupon.registerTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("couponName", searchBy)) {
            return QCoupon.coupon.couponName.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<CouponListDto> getAdminCouponPage(CouponSearchDto couponSearchDto, Pageable pageable) {
        QCoupon coupon = QCoupon.coupon;
        List<CouponListDto> content = queryFactory.select(new QCouponListDto(coupon.id, coupon.couponName, coupon.discount, coupon.couponDescription, coupon.couponType, coupon.registerTime))
                .from(coupon)
                .where(regDtsAfter(couponSearchDto.getSearchDateType()),
                        searchCouponTypeEq(couponSearchDto.getCouponType()),
                        searchByLike(couponSearchDto.getSearchBy(),
                                couponSearchDto.getSearchQuery()))
                .orderBy(QCoupon.coupon.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Coupon> countQuery = queryFactory.selectFrom(coupon)
                .where(regDtsAfter(couponSearchDto.getSearchDateType()),
                        searchCouponTypeEq(couponSearchDto.getCouponType()),
                        searchByLike(couponSearchDto.getSearchBy(),
                                couponSearchDto.getSearchQuery()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression couponNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QCoupon.coupon.couponName.like("%" + searchQuery + "%");
    }

}