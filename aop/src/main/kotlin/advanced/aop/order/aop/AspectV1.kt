package advanced.aop.order.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class AspectV1 {
    @Around("execution(* advanced.aop.order..*(..))")
    fun dolog(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        println("[start] dolog ${proceedingJoinPoint.signature}")
        return proceedingJoinPoint.proceed()
    }
}