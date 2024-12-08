package advanced.aop.order.aop.exam

import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.format.Printer
import kotlin.test.Test

@Import(
    TraceAspect::class,
    RetryAspect::class,
)
@SpringBootTest
class ExamServiceTest {
    @Autowired
    lateinit var examService: ExamService

    @Test
    fun time() {
        for (i in 1..5) {
            examService.request("number ${i}")
        }
    }
}