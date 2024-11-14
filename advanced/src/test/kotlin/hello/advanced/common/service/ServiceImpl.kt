package hello.advanced.common.service

import io.github.oshai.kotlinlogging.KotlinLogging

class ServiceImpl : ServiceInterface {
    private val logger = KotlinLogging.logger { }
    override fun save() {
        logger.info { "Saving data..." }
    }

    override fun find() {
        logger.info { "Found data..." }
    }
}