package hello.advanced.config

import hello.advanced.app.v2.OrderControllerV2
import hello.advanced.app.v2.OrderRepositoryV2
import hello.advanced.app.v2.OrderServiceV2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfigV2 {
    @Bean
    fun orderRepositoryV2() = OrderRepositoryV2()

    @Bean
    fun orderServiceV2() = OrderServiceV2(orderRepositoryV2())

    @Bean
    fun orderControllerV2() = OrderControllerV2(orderServiceV2())
}