package hello.advanced.app.v2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV2(
    private val orderService: OrderServiceV2
) {
    @GetMapping("/v2/request")
    fun request(itemId: String): String {
        orderService.orderItem(itemId)
        return "OK"
    }

    @GetMapping("/v2/no-log")
    fun noLog(): String {
        TODO("Not yet implemented")
    }
}