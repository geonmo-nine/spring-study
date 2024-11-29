package hello.advanced.config.v3_proxyfactory.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class LogAdvice : MethodInterceptor {
    val logger = KotlinLogging.logger { }
    override fun invoke(invocation: MethodInvocation): Any? {
        logger.info("Invoking ${invocation.method.declaringClass.name}#${invocation.method.name}")
        return invocation.proceed()
    }
}