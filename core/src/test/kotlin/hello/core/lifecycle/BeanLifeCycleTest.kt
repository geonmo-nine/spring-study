package hello.core.lifecycle

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BeanLifeCycleTest {
    @Test
    fun lifeCycleTest() {
        val ac = AnnotationConfigApplicationContext(LifeCycleConfig::class.java)
        val networkLifeCycle = ac.getBean(NetworkLifeCycle::class.java)
        ac.close()
    }

    @Configuration
    class LifeCycleConfig {
        fun beanLifeCycleTest() {
            @Bean(initMethod = "init", destroyMethod = "close")
            fun networkLifeCycle(): NetworkLifeCycle {
                val network = NetworkLifeCycle()
                network.setUrl("http://127.0.0.1:8080")
                return network
            }
        }
    }
}