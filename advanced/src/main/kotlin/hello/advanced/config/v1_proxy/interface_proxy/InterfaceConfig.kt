package hello.advanced.config.v1_proxy.interface_proxy

import hello.advanced.app.v1.OrderControllerV1Impl
import hello.advanced.app.v1.OrderRepositoryV1Impl
import hello.advanced.app.v1.OrderServiceImplV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InterfaceConfig {
    @Bean
    fun orderRepository() = OrderRepositoryInterfaceProxy(OrderRepositoryV1Impl())

    @Bean
    fun orderService() = OrderServiceInterfaceProxy(OrderServiceImplV1(orderRepository()))

    @Bean
    fun orderController() = OrderControllerInterfaceProxy(OrderControllerV1Impl(orderService()))
}