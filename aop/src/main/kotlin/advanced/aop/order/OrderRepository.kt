package advanced.aop.order

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
class OrderRepository {

    fun save(itemId: String):String  {
        println("[OrderRepository] Saving item $itemId]")
        if(itemId == "ex") {
            throw IllegalArgumentException("itemId is required")
        }

        return "ok"
    }
}