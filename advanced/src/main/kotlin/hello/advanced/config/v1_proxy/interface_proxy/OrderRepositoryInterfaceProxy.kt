package hello.advanced.config.v1_proxy.interface_proxy

import hello.advanced.app.v1.OrderRepositoryV1
import io.github.oshai.kotlinlogging.KotlinLogging

class OrderRepositoryInterfaceProxy(
    private val target: OrderRepositoryV1
) : OrderRepositoryV1 {
    private val logger = KotlinLogging.logger { }
    override fun save(itemId: String) {
        logger.info { "OrderRepositoryInterfaceProxy" }
        target.save(itemId)
    }
}