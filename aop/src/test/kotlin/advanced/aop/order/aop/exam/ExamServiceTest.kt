package advanced.aop.order.aop.exam

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import kotlin.test.Test

@Import(TraceAspect::class, RetryAspect::class)
@SpringBootTest
class ExamServiceTest {
    @Autowired
    lateinit var examService: ExamService

    @Test
    fun time() {
        for (i in 1..10) {
            examService.request("number ${i}")
        }
    }
}