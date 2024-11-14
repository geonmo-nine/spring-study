package hello.advanced.pureproxy.concreteproxy.code

import io.github.oshai.kotlinlogging.KotlinLogging

class ConcreteTimeProxy(
    val concreteLogic: ConcreteLogic
) : ConcreteLogic() {
    private val logger = KotlinLogging.logger {}
    override fun operate(): String {
        logger.info { "ConcreteTimeProxy" }
        return concreteLogic.operate()
    }
}