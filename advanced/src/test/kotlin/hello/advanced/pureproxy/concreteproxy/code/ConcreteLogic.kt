package hello.advanced.pureproxy.concreteproxy.code

import io.github.oshai.kotlinlogging.KotlinLogging

open class ConcreteLogic {
    private val logger = KotlinLogging.logger {}
    open fun operate(): String {
        logger.info { "ConcreteLogic operation started" }
        return "hello"
    }
}