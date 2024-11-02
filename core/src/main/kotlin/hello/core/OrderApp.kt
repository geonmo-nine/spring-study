package hello.core

import hello.core.member.Grade
import hello.core.member.Member
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl
import hello.core.order.OrderServiceImpl

fun main(args: Array<String>) {
    val appConfig = AppConfig()
    val memberService = appConfig.memberService()
    val orderService = appConfig.orderService()
    val member = Member(1, "name", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(member.id, "ItemA", 5000)

    println("order = ${order} ${order.calculatePrice}")
}