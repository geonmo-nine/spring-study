package advanced.aop.order.aop.exam

import advanced.aop.order.aop.exam.annotation.Retry
import advanced.aop.order.aop.exam.annotation.Trace
import org.springframework.stereotype.Service

@Service
class ExamService(
    private val examRepository: ExamRepository
) {
    @Trace
    @Retry(3)
    fun request(itemId: String) {
        println("i am " + this)
        examRepository.save(itemId)
    }
}