package hello.advanced.config

import hello.advanced.app.v1.OrderControllerV1Impl
import hello.advanced.app.v1.OrderRepositoryV1Impl
import hello.advanced.app.v1.OrderServiceImplV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfigV1 {
    @Bean
    fun orderRepository() = OrderRepositoryV1Impl()

    @Bean
    fun orderService() = OrderServiceImplV1(orderRepository())

    @Bean
    fun orderController() = OrderControllerV1Impl(orderService())
}