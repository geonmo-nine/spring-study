package hello.advanced.config.v1_proxy.interface_proxy

import hello.advanced.app.v1.OrderControllerV1
import io.github.oshai.kotlinlogging.KotlinLogging

class OrderControllerInterfaceProxy(
    private val target: OrderControllerV1
) : OrderControllerV1 {
    private val logger = KotlinLogging.logger { }
    override fun request(itemId: String): String {
        logger.info { "OrderControllerInterfaceProxy" }
        return target.request(itemId)
    }

    override fun noLog(): String {
        return target.noLog()
    }
}