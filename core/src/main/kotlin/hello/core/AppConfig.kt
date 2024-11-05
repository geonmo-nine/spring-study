package hello.core

import hello.core.discount.FixDiscountPolicy
import hello.core.member.MemberServiceImpl
import hello.core.member.MemoryMemberRepository
import hello.core.order.OrderServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun memberRepository() = MemoryMemberRepository()

    @Bean
    fun memberService() = MemberServiceImpl(memberRepository())

    @Bean
    fun discountPolicy() = FixDiscountPolicy()

    @Bean
    fun orderService() = OrderServiceImpl(memberRepository(), discountPolicy())
}