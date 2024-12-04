package advanced.aop.order.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

class AspectV5 {
    @Aspect
    @Order(2)
    class DoLog {
        @Around("advanced.aop.order.aop.Pointcuts.allOrder()")
        fun dolog(proceedingJoinPoint: ProceedingJoinPoint): Any? {
            println("[start] dolog ${proceedingJoinPoint.signature}")
            return proceedingJoinPoint.proceed()
        }
    }

    @Aspect
    @Order(1)
    class DoTransaction {
        @Around("advanced.aop.order.aop.Pointcuts.allOrderAndService()")
        fun doTransaction(proceedingJoinPoint: ProceedingJoinPoint): Any? {
            try {
                println("[start] doTransaction ${proceedingJoinPoint.signature}")
                return proceedingJoinPoint.proceed()
            } catch (e: Exception) {
                println("[rollback] doTransaction ${proceedingJoinPoint.signature}")
                throw IllegalArgumentException()
            }
        }
    }
}