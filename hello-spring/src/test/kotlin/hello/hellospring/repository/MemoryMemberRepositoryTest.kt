package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MemoryMemberRepositoryTest {
    private val memberRepository = MemoryMemberRepository()

    @AfterEach
    fun clearAll() {
        memberRepository.clearStore()
    }

    @Test
    fun save() {
        val member = Member(name = "test")
        memberRepository.save(member)

        val find  = memberRepository.findById(member.id!!)

        assertThat(find).isEqualTo(member)
    }

    @Test
    fun findById() {
    }

    @Test
    fun findByName() {
        println(memberRepository.findAll())
        val member1 = Member(name = "test")
        memberRepository.save(member1)

        val member2 = Member(name = "test2")
        memberRepository.save(member2)

        val find = memberRepository.findByName("test")
        assertThat(find).isEqualTo(member1)
    }

    @Test
    fun findAll() {
        println(memberRepository.findAll())
        val member1 = Member(name = "test")
        memberRepository.save(member1)

        val member2 = Member(name = "test2")
        memberRepository.save(member2)

        val finds  = memberRepository.findAll()
        assertThat(finds).hasSize(2)
    }
}

class AdditionTest {
    private var sum = 1

    @Test
    fun addingTwoReturnsThree() {
        println(sum)
        sum += 2
        assertEquals(3, sum)
    }

    @Test
    fun addingThreeReturnsFour() {
        println(sum)
        sum += 3
        assertEquals(4, sum)
    }
}