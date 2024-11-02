package hello.core.member

class MemberServiceImpl(
    private val memberRepository: MemberRepository
): MemberService {
    override fun join(member: Member) {
        memberRepository.save(member)
    }

    override fun findMember(id: Long): Member {
        return memberRepository.findById(id)
    }
}