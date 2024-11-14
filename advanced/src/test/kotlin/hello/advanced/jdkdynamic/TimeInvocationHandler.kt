package hello.advanced.jdkdynamic

import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class TimeInvocationHandler(
    private val target: Any
) : InvocationHandler {
    private val logger = KotlinLogging.logger { }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        logger.info { "invoke 실행" }
        val result = method?.invoke(target, *(args ?: emptyArray()))
        return result
    }
}