package hello.advanced.pureproxy.proxy.code

import io.github.oshai.kotlinlogging.KotlinLogging

class RealSubject : Subject {
    private val logger = KotlinLogging.logger { }
    override fun operate(): String {
        logger.info { "operation started" }
        Thread.sleep(1000)
        return "OK"
    }
}