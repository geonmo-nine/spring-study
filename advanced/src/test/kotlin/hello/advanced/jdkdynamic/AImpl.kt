package hello.advanced.jdkdynamic

import io.github.oshai.kotlinlogging.KotlinLogging

class AImpl : AInterface {
    private val logger = KotlinLogging.logger {}
    override fun call(): String {
        logger.info { "A Impl" }
        return "A"
    }

    override fun call2(): String {
        logger.info { "call 2!" }
        return "call2"
    }
}