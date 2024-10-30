package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class JdbcTemplateMemberRepository(
    dataSource: DataSource
) : MemberRepository {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    override fun save(member: Member): Member {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Member? {
        TODO("Not yet implemented")
    }

    override fun findByName(name: String): Member? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Member> {
        TODO("Not yet implemented")
    }
}