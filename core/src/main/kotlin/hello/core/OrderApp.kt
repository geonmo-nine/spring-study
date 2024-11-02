package hello.core

import hello.core.member.Grade
import hello.core.member.Member
import hello.core.member.MemberService
import hello.core.order.OrderService
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main(args: Array<String>) {
    val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
    val memberService = ac.getBean("memberService", MemberService::class.java)
    val orderService = ac.getBean("orderService", OrderService::class.java)
    val member = Member(1, "name", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(member.id, "ItemA", 5000)

    println("order = ${order} ${order.calculatePrice}")
}