package hello.advanced.app.v1

class OrderControllerV1Impl(
    private val orderService: OrderServiceV1
) : OrderControllerV1 {
    override fun request(itemId: String): String {
        orderService.orderItem(itemId)
        return "OK"
    }

    override fun noLog(): String {
        return "no-log"
    }
}