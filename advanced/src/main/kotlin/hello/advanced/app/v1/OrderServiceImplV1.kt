package hello.advanced.app.v1

open class OrderServiceImplV1(
    private val orderRepository: OrderRepositoryV1
) : OrderServiceV1 {
    override fun orderItem(itemId: String) {
        orderRepository.save(itemId)
    }
}