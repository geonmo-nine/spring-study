package advanced.aop.order.aop.exam

import advanced.aop.order.aop.exam.annotation.Retry
import advanced.aop.order.aop.exam.annotation.Trace
import org.springframework.stereotype.Repository

var seq = 0

@Repository
class ExamRepository {
    @Trace
    @Retry(3)
    fun save(itemId: String): String {
        seq++
        if (seq % 5 == 0) {
            throw IllegalArgumentException()
        }
        return "Ok"
    }
}