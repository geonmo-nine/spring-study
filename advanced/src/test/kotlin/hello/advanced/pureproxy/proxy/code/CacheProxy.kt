package hello.advanced.pureproxy.proxy.code

import io.github.oshai.kotlinlogging.KotlinLogging

class CacheProxy(
    private val target: Subject
) : Subject {
    private val logger = KotlinLogging.logger { }
    private val cacheValue: String by lazy { target.operate() }

    override fun operate(): String {
        logger.info { "CacheProxy: $cacheValue" }
        return cacheValue
    }
}