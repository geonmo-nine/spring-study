package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberServiceTest {
    private lateinit var memoryMemberRepository: MemoryMemberRepository
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        memoryMemberRepository = MemoryMemberRepository()
        memberService = MemberService(memoryMemberRepository)
    }

    @AfterEach
    fun clear() {
        memoryMemberRepository.clearStore()
    }

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