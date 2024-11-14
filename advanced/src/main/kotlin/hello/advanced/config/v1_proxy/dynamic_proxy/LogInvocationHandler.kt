package hello.advanced.config.v1_proxy.dynamic_proxy

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.util.PatternMatchUtils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LogInvocationHandler(
    private val target: Any
) : InvocationHandler {
    private val logger = KotlinLogging.logger { }
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (!PatternMatchUtils.simpleMatch("noLog*", method?.name)) {
            logger.info { "Log invocation handler ${method?.name}" }
        }
        val result = method?.invoke(target, *(args ?: emptyArray()))
        return result
    }
}