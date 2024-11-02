package hello.core.discount

import hello.core.member.Grade
import hello.core.member.Member

const val discountRatePercent = 10
class RateDiscountPolicy: DiscountPolicy {
    override fun discount(member: Member, price: Int): Int = when(member.grade) {
        Grade.VIP -> (price * discountRatePercent)/100
        else  -> 0
    }
}