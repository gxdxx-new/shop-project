package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.OrderDto;
import com.gxdxx.shop.entity.Item;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.entity.Order;
import com.gxdxx.shop.entity.OrderItem;
import com.gxdxx.shop.repository.ItemRepository;
import com.gxdxx.shop.repository.MemberRepository;
import com.gxdxx.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

}