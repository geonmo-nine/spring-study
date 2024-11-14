package hello.advanced

import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.test.assertEquals

class BeanSingletonTest {
    @Configuration
    class MyConfig {

        @Bean
        fun param() = Param("BEAN PARAM")

        @Bean
        fun myService(param: Param): MyService {
            println("Creating MyService instance $param")
            return MyService(param)
        }

        @Bean
        fun anotherBean(): AnotherBean {
            val instance1 = myService(Param("무의미한 파라메터1")) // 첫 번째 호출
            val instance2 = myService(Param("무의미한 파라메터2")) // 두 번째 호출
            return AnotherBean(instance1, instance2)
        }
    }

    data class Param(val name: String)

    data class MyService(val param: Param)

    class AnotherBean(val service1: MyService, val service2: MyService)

    @Test
    fun ConfigTest() {
        val ac = AnnotationConfigApplicationContext(MyConfig::class.java)
        val myServiceBean = ac.getBean(MyService::class.java)
        val anotherBean = ac.getBean(AnotherBean::class.java)
        assertEquals(anotherBean.service1, anotherBean.service2, "넘겨진 파라메터와 관계없이 매서드 이름만 가지고 싱글톤 빈을 사용한다")
        assertEquals(myServiceBean, anotherBean.service1, "싱글톤 빈인 등록된 빈과 동일하다")
        assertEquals(myServiceBean.param, anotherBean.service1.param, "당연히 프로퍼티도 동일하다")
    }
}