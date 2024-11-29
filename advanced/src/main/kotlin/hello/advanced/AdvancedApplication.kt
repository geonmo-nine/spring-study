package hello.advanced

import hello.advanced.config.v4_postprocessor.BeanPostProcessorConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(BeanPostProcessorConfig::class)
@SpringBootApplication(scanBasePackages = ["hello.advanced.app.v1"])
class AdvancedApplication

fun main(args: Array<String>) {
    runApplication<AdvancedApplication>(*args)
}
