package com.gxdxx.shop.service;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.constant.OrderStatus;
import com.gxdxx.shop.dto.MemberFormDto;
import com.gxdxx.shop.dto.OrderDto;
import com.gxdxx.shop.entity.Item;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.entity.Order;
import com.gxdxx.shop.entity.OrderItem;
import com.gxdxx.shop.repository.ItemRepository;
import com.gxdxx.shop.repository.MemberRepository;
import com.gxdxx.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Item saveItem() {
        Item item = Item.builder()
                .itemName("테스트 상품")
                .price(10000)
                .itemDescription("테스트 상품 상세 설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockQuantity(100)
                .build();
        return itemRepository.save(item);
    }

    public MemberFormDto createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("경북 경산시 대동");
        memberFormDto.setPassword("123123");
        return memberFormDto;
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        MemberFormDto memberFormDto = this.createMember();
        Member member = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() {
        Item item = saveItem();
        MemberFormDto memberFormDto = this.createMember();
        Member member = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());
        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(100, item.getStockQuantity());
    }

}