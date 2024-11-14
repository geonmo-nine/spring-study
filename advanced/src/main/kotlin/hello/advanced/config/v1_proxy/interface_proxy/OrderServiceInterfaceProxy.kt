package hello.advanced.config.v1_proxy.interface_proxy

import hello.advanced.app.v1.OrderServiceV1
import io.github.oshai.kotlinlogging.KotlinLogging

class OrderServiceInterfaceProxy(
    private val target: OrderServiceV1
) : OrderServiceV1 {
    private val logger = KotlinLogging.logger { }
    override fun orderItem(itemId: String) {
        logger.info { "OrderServiceInterfaceProxy" }
        target.orderItem(itemId)
    }
}