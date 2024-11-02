package hello.hellospring

import hello.hellospring.repository.MemberRepository
import hello.hellospring.service.MemberService
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class SpringConfig(
    val dataSource: DataSource,
    val em: EntityManager,
    val memberRepository: MemberRepository
) {
    @Bean
    fun memberService(): MemberService {
        println(memberRepository)

        return MemberService(memberRepository)
    }

//    @Bean
//    fun memberRepository(): MemberRepository {
//        return MemoryMemberRepository()
//        return JdbcMemberRepository(dataSource)
//        return JpaMemberRepository(em)
//    }
}