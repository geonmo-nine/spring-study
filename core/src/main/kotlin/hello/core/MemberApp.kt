package hello.core

import hello.core.member.Grade
import hello.core.member.Member
import hello.core.member.MemberService
import hello.core.member.MemberServiceImpl

    fun main(args: Array<String>) {
        val appConfig = AppConfig()
        val memberService = appConfig.memberService()
        val member = Member(1, "name", Grade.VIP)
        memberService.join(member)

        val findMember = memberService.findMember(member.id)
        println("findMember = ${findMember}")
        println("member = ${member}")
        
    }