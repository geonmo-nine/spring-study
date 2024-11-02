package hello.core

import hello.core.member.Grade
import hello.core.member.Member
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main(args: Array<String>) {
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
        val memberService = ac.getBean(MemberService::class.java)
        val member = Member(1, "name", Grade.VIP)
        memberService.join(member)

        val findMember = memberService.findMember(member.id)
        println("findMember = ${findMember}")
        println("member = ${member}")
        
    }