package advanced.aop.order.aop.member

import advanced.aop.order.aop.member.annotation.ClassAop
import advanced.aop.order.aop.member.annotation.MethodAop
import org.springframework.stereotype.Component

@ClassAop
@Component
class MemberServiceImpl : MemberService {

    @MethodAop("test value")
    override fun hello(name: String): String {
        return "Hello, $name!"
    }

    fun internal(): String {
        return "internal"
    }
}