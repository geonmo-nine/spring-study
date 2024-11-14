package hello.advanced.cblib

import hello.advanced.cblib.code.TimeInterceptor
import hello.advanced.common.service.ConcreteService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.cglib.proxy.Enhancer

class CglibTest {
    private val logger = KotlinLogging.logger { }

    @Test
    fun cglibTest() {
        val concreteService = ConcreteService()
        val enhancer = Enhancer()
        enhancer.setSuperclass(ConcreteService::class.java)
        enhancer.setCallback(TimeInterceptor(concreteService))
        val proxy = enhancer.create() as ConcreteService
        proxy.call()
        
        logger.info { "proxyClass ${proxy::class.java}" }
    }
}