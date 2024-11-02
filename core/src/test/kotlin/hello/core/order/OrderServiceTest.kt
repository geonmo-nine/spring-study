package hello.core.order

import hello.core.AppConfig
import hello.core.discount.discountFixAmount
import hello.core.member.Grade
import hello.core.member.Member
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {
    lateinit var memberService: MemberService
    lateinit var orderService: OrderService

    @BeforeEach
    fun setup() {
        val appConfig = AppConfig()
        memberService = appConfig.memberService()
        orderService = appConfig.orderService()
    }

    @Test
    fun order() {
        val member = Member(1, "Smith", Grade.VIP)
        memberService.join(member)
        val order = orderService.createOrder(member.id, "ITEMA", 5000)
        assertThat(order.calculatePrice).isEqualTo(4000)
        assertThat(order.discountPrice).isEqualTo(1000)
    }
}