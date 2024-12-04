package advanced.aop.order

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun orderItem(itemId: String) {
        println("[OrderService] Ordering item $itemId]")
        orderRepository.save(itemId)
    }
}