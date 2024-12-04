package advanced.aop.order.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class AspectV4 {

    @Around("advanced.aop.order.aop.Pointcuts.allOrder()")
    fun dolog(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        println("[start] dolog ${proceedingJoinPoint.signature}")
        return proceedingJoinPoint.proceed()
    }

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