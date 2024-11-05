package hello.core.beanfind

import hello.core.AppConfig
import hello.core.discount.DiscountPolicy
import hello.core.discount.FixDiscountPolicy
import hello.core.discount.RateDiscountPolicy
import hello.core.member.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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

    @Test
    fun `타입으로 빈 검색시 복수개이면 에러`() {
        val ac = AnnotationConfigApplicationContext(SameBeanConfig::class.java)
        assertThrows<NoUniqueBeanDefinitionException> { ac.getBean(DiscountPolicy::class.java) }
    }

    @Test
    fun `타입으로 빈 검색시 복수개이면 이름으로 조회해야한다`() {
        val ac = AnnotationConfigApplicationContext(SameBeanConfig::class.java)
        val bean = ac.getBean("fixDiscountPolicy", DiscountPolicy::class.java)
        assertThat(bean).isInstanceOf(DiscountPolicy::class.java)
    }

    @Test
    fun `타입으로 모두 출력하기`() {
        val ac = AnnotationConfigApplicationContext(SameBeanConfig::class.java)
        val beansOfType = ac.getBeansOfType(DiscountPolicy::class.java)
        for ((key, bean) in beansOfType) {
            println("key = ${key} bean = ${bean}")
        }
        assertThat(beansOfType.size).isEqualTo(2)
    }


    @Configuration
    class SameBeanConfig {
        @Bean
        fun fixDiscountPolicy(): DiscountPolicy = FixDiscountPolicy()

        @Bean
        fun rateDiscountPolicy(): DiscountPolicy = RateDiscountPolicy()
    }
}