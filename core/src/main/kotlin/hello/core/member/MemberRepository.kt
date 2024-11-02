package hello.core.member

interface MemberRepository {
    fun findById(id: Long): Member
    fun save(member: Member)
}