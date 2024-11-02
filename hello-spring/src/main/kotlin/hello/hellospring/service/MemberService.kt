package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository

open class MemberService(
    val memberRepository: MemberRepository
) {
    fun join(member: Member): Long? {
        validateDuplicateMember(member)

        val saved = memberRepository.save(member)
        return saved.id
    }

    open fun findMembers(): List<Member> = memberRepository.findAll()

    fun findOne(id: Long) = memberRepository.findById(id)

    private fun validateDuplicateMember(member: Member) {
        val find = memberRepository.findByName(member.name)
        find?.let { throw IllegalStateException() }
    }
}