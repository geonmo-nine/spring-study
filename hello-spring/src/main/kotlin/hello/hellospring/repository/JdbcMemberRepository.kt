package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.*
import javax.sql.DataSource

class JdbcMemberRepository(
    private val dataSource: DataSource
) : MemberRepository {
    private fun getConnection(): Connection {
        return DataSourceUtils.getConnection(dataSource)
    }

    private fun close(conn: Connection) {
        DataSourceUtils.releaseConnection(conn, dataSource)
    }

    private fun close(conn: Connection, pstmt: PreparedStatement, rs: ResultSet) {
        try {
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            pstmt.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            close(conn)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun save(member: Member): Member {
        val sql = "insert into member(name) values(?)"
        lateinit var conn: Connection
        lateinit var pstmt: PreparedStatement
        lateinit var rs: ResultSet
        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            pstmt.setString(1, member.name)
            pstmt.executeUpdate()
            rs = pstmt.generatedKeys
            if (rs.next()) {
                member.id = rs.getLong(1)
            } else {
                throw SQLException("id 조회 실패")
            }
            return member
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }

    override fun findById(id: Long): Member? {
        val sql = "select * from member where id = ?"
        lateinit var conn: Connection
        lateinit var pstmt: PreparedStatement
        lateinit var rs: ResultSet
        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setLong(1, id)
            rs = pstmt.executeQuery()
            if (rs.next()) {
                val member = Member(rs.getLong("id"), rs.getString("name"))
                return member
            } else {
                return null
            }
        } catch (e: java.lang.Exception) {
            throw java.lang.IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }

    override fun findByName(name: String): Member? {
        val sql = "select * from member where name = ?"
        lateinit var conn: Connection
        lateinit var pstmt: PreparedStatement
        lateinit var rs: ResultSet
        try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, name)
            rs = pstmt.executeQuery()
            if (rs.next()) {
                val member = Member(rs.getLong("id"), rs.getString("name"))
                return member
            } else {
                return null
            }
        } catch (e: java.lang.Exception) {
            throw java.lang.IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }

    override fun findAll(): List<Member> {
        val sql = "select * from member"
        lateinit var conn: Connection
        lateinit var pstmt: PreparedStatement
        lateinit var rs: ResultSet
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            val members = mutableListOf<Member>()
            while (rs.next()) {
                val member = Member(rs.getLong("id"), rs.getString("name"))
                members.add(member);
            }
            return members;
        } catch (e: java.lang.Exception) {
            throw java.lang.IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }
}