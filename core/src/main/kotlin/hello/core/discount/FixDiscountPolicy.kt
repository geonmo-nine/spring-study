package hello.core.discount

import hello.core.member.Grade
import hello.core.member.Member

const val discountFixAmount = 1000
class FixDiscountPolicy: DiscountPolicy {
    override fun discount(member: Member, price: Int): Int {
        return when (member.grade) {
            Grade.BASIC  -> 0
            Grade.VIP  -> discountFixAmount
        }
    }
}