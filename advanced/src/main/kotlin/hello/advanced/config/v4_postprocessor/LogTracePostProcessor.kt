package hello.advanced.config.v4_postprocessor

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.aop.Advisor
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.factory.config.BeanPostProcessor

class LogTracePostProcessor(
    private val basePackageName: String,
    private val advisor: Advisor
) : BeanPostProcessor {
    private val logger = KotlinLogging.logger { }
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (basePackageName in bean.javaClass.packageName) {
            val proxyFactory = ProxyFactory(bean)
            proxyFactory.addAdvisor(advisor)
            val proxy = proxyFactory.proxy
            logger.info { "Bean name: $beanName, Bean type: ${bean::class.java.name}" }
            logger.info { "Proxy type: ${proxy::class.java.name}" }

            return proxy
        } else {
            return bean
        }
    }
}