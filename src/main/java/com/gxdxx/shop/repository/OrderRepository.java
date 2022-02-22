package com.gxdxx.shop.repository;

import com.gxdxx.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}