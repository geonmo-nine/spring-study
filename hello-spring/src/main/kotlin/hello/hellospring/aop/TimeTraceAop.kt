package hello.hellospring.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))")
    fun execute(joinPoint: ProceedingJoinPoint): Any {
        val startTime = System.currentTimeMillis()
        println("[start] ${joinPoint}")
        try {
            return joinPoint.proceed()
        } finally {
            val endTime = System.currentTimeMillis()
            println("[end] ${joinPoint} ${endTime - startTime}")
        }
    }
}