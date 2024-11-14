package hello.advanced.config.v1_proxy.dynamic_proxy

import hello.advanced.app.v1.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Proxy

@Configuration
class DynamicProxyConfig {
    @Bean
    fun orderRepository(): OrderRepositoryV1 {
        val target = LogInvocationHandler(OrderRepositoryV1Impl())
        val proxy = Proxy.newProxyInstance(
            OrderRepositoryV1::class.java.classLoader,
            arrayOf(OrderRepositoryV1::class.java),
            target
        ) as OrderRepositoryV1
        return proxy
    }

    @Bean
    fun orderService(): OrderServiceV1 {
        val target = LogInvocationHandler(OrderServiceImplV1(orderRepository()))
        val proxy = Proxy.newProxyInstance(
            OrderServiceV1::class.java.classLoader,
            arrayOf(OrderServiceV1::class.java),
            target
        ) as OrderServiceV1
        return proxy
    }

    @Bean
    fun orderController(): OrderControllerV1 {
        val target =
            LogInvocationHandler(OrderControllerV1Impl(orderService()))
        val proxy = Proxy.newProxyInstance(
            OrderControllerV1::class.java.classLoader,
            arrayOf(OrderControllerV1::class.java),
            target
        ) as OrderControllerV1
        return proxy
    }
}