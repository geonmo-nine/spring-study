package hello.advanced.postprocessor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BeanPostProcesssorTest {
    @Test
    fun test() {
        val context = AnnotationConfigApplicationContext(Config::class.java)
        val beanA = context.getBean("beanA")
        assertThat(beanA).isInstanceOf(B::class.java)

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

        @Bean
        fun postProcessorBean() = AtoBPostProcessor()
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

    class AtoBPostProcessor : BeanPostProcessor {
        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            return if (bean is A) {
                B()
            } else {
                bean
            }
        }
    }
}