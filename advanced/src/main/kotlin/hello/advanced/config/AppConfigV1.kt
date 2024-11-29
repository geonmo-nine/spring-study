package hello.advanced.config

import hello.advanced.app.v1.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfigV1 {
    @Bean
    fun orderRepository(): OrderRepositoryV1 = OrderRepositoryV1Impl()

    @Bean
    fun orderService(): OrderServiceV1 = OrderServiceImplV1(orderRepository())

    @Bean
    fun orderController(): OrderControllerV1 = OrderControllerV1Impl(orderService())
}