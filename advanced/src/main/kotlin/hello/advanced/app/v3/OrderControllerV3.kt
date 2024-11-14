package hello.advanced.app.v3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderControllerV3(
    private val orderServiceV3: OrderServiceV3
) {
    @GetMapping("/v3/request")
    fun request(itemId: String): String {
        orderServiceV3.orderItem(itemId)
        return "OK"
    }

    @GetMapping("/v3/no-log")
    fun noLog(): String {
        TODO("Not yet implemented")
    }
}