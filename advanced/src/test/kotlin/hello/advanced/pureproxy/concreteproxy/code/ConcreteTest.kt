package hello.advanced.pureproxy.concreteproxy.code

import org.junit.jupiter.api.Test

class ConcreteTest {
    @Test
    fun noProxy() {
        val concreteClient = ConcreteClient(ConcreteLogic())
        concreteClient.execute()
    }

    @Test
    fun timeProxy() {
        val concreteClient = ConcreteClient(ConcreteTimeProxy(ConcreteLogic()))
        concreteClient.execute()
    }
}