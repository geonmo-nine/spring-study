package hello.advanced.app.v2

class OrderServiceV2(
    val orderRepository: OrderRepositoryV2
) {
    fun orderItem(itemId: String) {
        orderRepository.save(itemId)
    }
}