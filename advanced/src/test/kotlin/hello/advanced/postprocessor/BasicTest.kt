package hello.advanced.postprocessor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BasicTest {
    @Test
    fun test() {
        val context = AnnotationConfigApplicationContext(Config::class.java)
        val beanA = context.getBean("beanA")
        assertThat(beanA).isInstanceOf(A::class.java)

        assertThrows<NoSuchBeanDefinitionException> {
            context.getBean("beanB")
        }
    }

    @Configuration
    class Config {
        @Bean
        fun beanA(): A {
            return A()
        }
    }

    class A {
        fun helloA() {
            println("helloA")
        }
    }

    class B {
        fun helloB() {
            println("helloB")
        }
    }
}