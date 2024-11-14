package hello.advanced

import hello.advanced.config.v1_proxy.dynamic_proxy.DynamicProxyConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(DynamicProxyConfig::class)
@SpringBootApplication(scanBasePackages = ["hello.advanced.app.v1"])
class AdvancedApplication

fun main(args: Array<String>) {
    runApplication<AdvancedApplication>(*args)
}
