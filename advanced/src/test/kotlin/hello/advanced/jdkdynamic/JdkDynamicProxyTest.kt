package hello.advanced.jdkdynamic

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import java.lang.reflect.Proxy

class JdkDynamicProxyTest {
    private val logger = KotlinLogging.logger {}

    @Test
    fun dynamicA() {
        val target = AImpl()
        val handler = TimeInvocationHandler(target)

        val proxy =
            Proxy.newProxyInstance(
                AInterface::class.java.classLoader,
                arrayOf(AInterface::class.java),
                handler
            ) as AInterface

        proxy.call()
        proxy.call2()


//        logger.info { "targetClass = ${target.javaClass}" }
//        logger.info { "proxyClass = ${proxy.javaClass}" }
//
//        logger.info { "target = ${target}" }
//        logger.info { "proxy = ${proxy}" }
    }
}