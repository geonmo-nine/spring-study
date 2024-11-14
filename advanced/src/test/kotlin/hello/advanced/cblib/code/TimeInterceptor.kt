package hello.advanced.cblib.code

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.cglib.proxy.MethodProxy
import java.lang.reflect.Method

class TimeInterceptor(
    val target: Any
) : MethodInterceptor {
    private val logger = KotlinLogging.logger {}
    override fun intercept(obj: Any?, method: Method?, args: Array<out Any>?, proxy: MethodProxy?): Any? {
        logger.info { "time interceptor " }
        return proxy?.invoke(target, args)
    }
}