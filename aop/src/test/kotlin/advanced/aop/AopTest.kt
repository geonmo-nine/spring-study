package advanced.aop

import advanced.aop.order.OrderRepository
import advanced.aop.order.OrderService
import advanced.aop.order.aop.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(AspectV5.DoLog::class, AspectV5.DoTransaction::class)
@SpringBootTest
class AopTest {
    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Test
    fun isAop() {
        println("is Aop ${AopUtils.isAopProxy(orderRepository)}")
        println("is Aop ${AopUtils.isAopProxy(orderService)}")
    }

    @Test
    fun success() {
        orderService.orderItem("item")
    }

    @Test
    fun failure() {
        assertThrows<IllegalArgumentException> { orderService.orderItem("ex") }
    }
}