package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.stereotype.Repository

@Repository
class MemoryMemberRepository : MemberRepository {
    override fun save(member: Member): Member {
        member.id = sequence++
        hashMap.put(member.id!!, member)
        return member
    }

    override fun findById(id: Long): Member? {
        return hashMap.get(id)
    }

    override fun findByName(name: String): Member? {
        return hashMap.values.find { member -> member.name == name }
    }

    override fun findAll(): List<Member> {
        return hashMap.values.toList()
    }

    fun clearStore() {
        hashMap.clear()
    }

    companion object {
        val hashMap = HashMap<Long, Member>()
        var sequence = 0L
    }
}