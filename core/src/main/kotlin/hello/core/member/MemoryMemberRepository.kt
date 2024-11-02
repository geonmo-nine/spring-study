package hello.core.member

class MemoryMemberRepository: MemberRepository {
    override fun findById(id: Long): Member {
        val member = store[id]
        return member ?: throw IllegalStateException("Member not found")
    }

    override fun save(member: Member) {
        store[member.id] = member
    }

    companion object {
        val store = HashMap<Long, Member>()
    }
}