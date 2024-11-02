package hello.core

import hello.core.discount.FixDiscountPolicy
import hello.core.discount.RateDiscountPolicy
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl
import hello.core.member.MemoryMemberRepository
import hello.core.order.OrderServiceImpl

class AppConfig {
    fun memberRepository() = MemoryMemberRepository()
    fun memberService() = MemberServiceImpl(memberRepository())
    fun discountPolicy() = FixDiscountPolicy()
    fun orderService() = OrderServiceImpl(memberRepository(),discountPolicy())
}