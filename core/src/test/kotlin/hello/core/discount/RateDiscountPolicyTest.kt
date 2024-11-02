package hello.core.discount

import hello.core.member.Grade
import hello.core.member.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RateDiscountPolicyTest {
    val discountPolicy = RateDiscountPolicy()
    @Test
    fun `VIP 는 10프로 할인이 되어야한다`() {
        val member = Member(1, "name", Grade.VIP)
        val discount = discountPolicy.discount(member, 10000)
        assertThat(discount).isEqualTo(1000)
    }

    @Test
    fun `VIP아니면 10할인 적용되지 않는다`() {
        val member = Member(1, "name", Grade.BASIC)
        val discount = discountPolicy.discount(member, 10000)
        assertThat(discount).isEqualTo(0)
    }
}