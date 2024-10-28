package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository
) {
    fun join(member: Member): Long? {
        validateDuplicateMember(member)

        val saved = memberRepository.save(member)
        return saved.id
    }

    fun findMembers() = memberRepository.findAll()
    fun findOne(id: Long) = memberRepository.findById(id)

    private fun validateDuplicateMember(member: Member) {
        val find = memberRepository.findByName(member.name)
        find?.let { throw IllegalStateException() }
    }
}