package hello.advanced.common.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class TimeAdvice : MethodInterceptor {
    private val logger = KotlinLogging.logger { }
    override fun invoke(invocation: MethodInvocation): Any? {
        logger.info { "time advice call" }
        return invocation.proceed()
    }
}