package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var memberService: MemberService

    @Test
    fun join() {
        val member = Member(name = "John")
        val joinId = memberService.join(member)

        assertThat(member.id).isEqualTo(joinId)
    }

    @Test
    fun 중복_회원_예외() {
        val member1 = Member(name = "John")
        val member2 = Member(name = "John")

        memberService.join(member1)
        assertThrows<IllegalStateException> { memberService.join(member2) }
    }

    @Test
    fun findAll() {
        val member = Member(name = "John")
        val member2 = Member(name = "John2")
        memberService.join(member)
        memberService.join(member2)

        val findmembers = memberService.findMembers()
        assertThat(findmembers).hasSize(2)
    }

    @Test
    fun findOne() {
        val member = Member(name = "John")
        val member2 = Member(name = "John2")
        memberService.join(member)
        memberService.join(member2)

        val findmember = memberService.findOne(member2.id!!)
        assertThat(findmember).isEqualTo(member2)
    }
}