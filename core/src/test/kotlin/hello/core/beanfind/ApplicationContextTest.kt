package hello.core.beanfind

import hello.core.AppConfig
import hello.core.member.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ApplicationContextTest {
    val ac = AnnotationConfigApplicationContext(AppConfig::class.java)

    @Test
    fun findBean() {
        for (name in ac.beanDefinitionNames) {
            val bean = ac.getBean(name)
            println("name = ${name} bean = ${bean}")
        }
    }

    @Test
    fun findApplicationBean() {
        for (name in ac.beanDefinitionNames) {
            val bean = ac.getBean(name)
            val beanDefinition = ac.getBeanDefinition(name)
            if (beanDefinition.role == BeanDefinition.ROLE_APPLICATION)
                println("name = ${name} bean = ${bean}")
        }
    }

    @Test
    fun `빈 이름으로 조회`() {
        val memberService = ac.getBean("memberService")
        assertThat(memberService).isInstanceOf(MemberService::class.java)
    }

    @Test
    fun `빈 타입으로 조회`() {
        val memberService = ac.getBean(MemberService::class.java)
        assertThat(memberService).isInstanceOf(MemberService::class.java)
    }

    @Test
    fun `빈이름이 없을때 에러`() {
        assertThrows<NoSuchBeanDefinitionException> {
            ac.getBean("unknown")
        }
    }

    @Test
    fun `빈 이름과 타입이 불일치시 에러`() {
        assertThrows<NoSuchBeanDefinitionException> {
            ac.getBean("unknown", MemberService::class.java)
        }

        assertThatThrownBy { ac.getBean("unknown", MemberService::class.java) }
            .isInstanceOf(NoSuchBeanDefinitionException::class.java)
    }
}