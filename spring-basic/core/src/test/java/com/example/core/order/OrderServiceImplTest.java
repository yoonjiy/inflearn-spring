package com.example.core.order;

import com.example.core.discount.FixDiscountPolicy;
import com.example.core.member.Grade;
import com.example.core.member.Member;
import com.example.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder(){
        //OrderServiceImpl orderService = new OrderServiceImpl();
        // 생성자 주입은 컴파일 오류 발생, 수정자 주입은 실행은 되지만 NPE 발생!
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1l, "memberA", Grade.BASIC));
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getItemPrice()).isEqualTo(10000);


    }

}