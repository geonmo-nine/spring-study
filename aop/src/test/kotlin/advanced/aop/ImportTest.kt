package advanced.aop

import advanced.aop.order.aop.AspectV5
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Import

class ImportTest {
    @Test
    fun actest() {
        val ac = AnnotationConfigApplicationContext(AspectV5::class.java)
        ac.beanDefinitionNames.forEach { println(it) }
    }
}