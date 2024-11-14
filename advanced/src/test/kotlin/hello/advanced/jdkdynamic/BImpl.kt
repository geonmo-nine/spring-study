package hello.advanced.jdkdynamic

import io.github.oshai.kotlinlogging.KotlinLogging

class BImpl : BInterface {
    private val logger = KotlinLogging.logger {}
    override fun call(): String {
        logger.info { "A Impl" }
        return "B"
    }
}