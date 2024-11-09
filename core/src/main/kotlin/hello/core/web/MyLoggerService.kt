package hello.core.web

import hello.core.common.MyLogger
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service

@Service
class MyLoggerService(
    private val myLoggerProvider: ObjectProvider<MyLogger>,
) {
    fun loggerService() {
        val myLogger = myLoggerProvider.`object`
        myLogger.log("service test")
    }
}