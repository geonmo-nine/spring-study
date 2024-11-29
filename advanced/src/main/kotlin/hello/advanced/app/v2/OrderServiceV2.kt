package hello.advanced.app.v2

open class OrderServiceV2(
    private val orderRepository: OrderRepositoryV2
) {
    fun orderItem(itemId: String) {
        println("------------")
        println(this)
        println(this.orderRepository)
        println("--------${orderRepository.javaClass}")
        orderRepository.save(itemId)
    }
}