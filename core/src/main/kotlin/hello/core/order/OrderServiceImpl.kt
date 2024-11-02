package hello.core.order

import hello.core.discount.DiscountPolicy
import hello.core.discount.FixDiscountPolicy
import hello.core.member.MemberRepository
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl

class OrderServiceImpl(
    private val memberRepository: MemberRepository,
    private val discountPolicy: DiscountPolicy
): OrderService {

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        val findMember = memberRepository.findById(memberId)
        val discountPrice = discountPolicy.discount(findMember, itemPrice)
        return Order(1, itemName, itemPrice, discountPrice)
    }
}