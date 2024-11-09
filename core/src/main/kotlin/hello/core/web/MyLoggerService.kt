package hello.core.web

import hello.core.common.MyLogger
import org.springframework.stereotype.Service

@Service
class MyLoggerService(
    private val myLogger: MyLogger,
) {
    fun loggerService() {
        myLogger.log("service test")
    }
}