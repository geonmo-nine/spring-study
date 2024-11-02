package hello.core.member

import hello.core.AppConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberServiceTest {
    lateinit var  memberService: MemberService

    @BeforeEach
    fun setUp() {
        val appConfig = AppConfig()
        memberService = appConfig.memberService()
    }

    @Test
    fun join() {
        val member = Member(1, "name", Grade.BASIC)

        memberService.join(member)

        val findmember = memberService.findMember(member.id)
        assertEquals(findmember, member)
    }
}