package advanced.aop.order.aop.exam

import advanced.aop.order.aop.exam.annotation.Retry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class RetryAspect {
    @Around("@annotation(retry)")
    fun aroundRetry(joinPoint: ProceedingJoinPoint, retry: Retry): Any? {
        val count = retry.value
        lateinit var exception: Exception

        for (i in 1..count) {
            try {
                val result = joinPoint.proceed()
                println("success count $i")
                return result
            } catch (e: RuntimeException) {
                println("error count $i / $count")
                exception = e
            }
        }
        throw exception
    }
}