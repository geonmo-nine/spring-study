package hello.advanced.common.service

import io.github.oshai.kotlinlogging.KotlinLogging

open class ConcreteService {
    open val logger = KotlinLogging.logger {}
    fun call() {
        logger.info { "concrete service call" }
    }
}