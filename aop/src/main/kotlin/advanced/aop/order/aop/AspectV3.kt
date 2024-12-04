package advanced.aop.order.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class AspectV3 {

    // pointcut signature
    @Pointcut("execution(* advanced.aop.order..*(..))")
    fun allOrder() {
    }

    @Pointcut("execution(* *..*Service.*(..))")
    fun allService() {
    }

    @Around("allOrder()")
    fun dolog(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        println("[start] dolog ${proceedingJoinPoint.signature}")
        return proceedingJoinPoint.proceed()
    }

    @Around("allOrder() && allService()")
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