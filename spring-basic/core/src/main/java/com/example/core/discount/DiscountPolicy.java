package com.example.core.discount;

import com.example.core.member.Member;

/*
* @return 할인 대상 금액
* */
public interface DiscountPolicy {
    int discount(Member member, int price);
}
