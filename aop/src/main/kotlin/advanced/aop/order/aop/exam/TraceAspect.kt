package advanced.aop.order.aop.exam

import advanced.aop.order.aop.exam.annotation.Trace
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

@Aspect
class TraceAspect {
    @Before("@annotation(advanced.aop.order.aop.exam.annotation.Trace)")
    fun doTrace(joinPoint: JoinPoint) {
        println("args ${joinPoint.signature} ${joinPoint.args.joinToString(",")}")
    }
}