package hello.core.common

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import java.util.*

@Component
@Scope("request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class MyLogger {
    lateinit var uuid: String
    lateinit var requestUrl: String


    fun log(message: String) {
        println("[$uuid]:[$requestUrl] $message")
    }

    @PostConstruct
    fun init() {
        uuid = UUID.randomUUID().toString()
        println("[$uuid]: requestScope create")
    }

    @PreDestroy
    fun onDestroy() {
        println("[$uuid]: requestScope close")
    }
}