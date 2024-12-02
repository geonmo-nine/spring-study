package hello.advanced.config.v6_aop.aspect

import io.github.oshai.kotlinlogging.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class LogTraceAspect {
    val logger = KotlinLogging.logger { }

    @Around("execution(* hello.advanced.app.v1..*(..))")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature
        
        logger.info("Invoking ${signature.toShortString()}")
        return joinPoint.proceed()
    }
}