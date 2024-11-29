package hello.advanced.config.v3_proxyfactory

import hello.advanced.app.v1.*
import hello.advanced.config.v3_proxyfactory.advice.LogAdvice
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProxyFactoryConfigV1 {

    fun getAdvisor(): DefaultPointcutAdvisor {
        val pointcut = NameMatchMethodPointcut().apply { setMappedNames("request*", "save", "order*") }
        return DefaultPointcutAdvisor(pointcut, LogAdvice())
    }

    @Bean
    fun orderRepository(): OrderRepositoryV1 {
        val proxyFactory = ProxyFactory(OrderRepositoryV1Impl())
        proxyFactory.addAdvisor(getAdvisor())
        val proxy = proxyFactory.proxy as OrderRepositoryV1

        return proxy
    }

    @Bean
    fun orderService(): OrderServiceV1 {
        val proxyFactory = ProxyFactory(OrderServiceImplV1(orderRepository()))
        proxyFactory.addAdvisor(getAdvisor())
        val proxy = proxyFactory.proxy as OrderServiceV1

        return proxy
    }

    @Bean
    fun orderController(): OrderControllerV1 {
        val proxyFactory = ProxyFactory(OrderControllerV1Impl(orderService()))
        proxyFactory.addAdvisor(getAdvisor())
        val proxy = proxyFactory.proxy as OrderControllerV1

        return proxy
    }
}