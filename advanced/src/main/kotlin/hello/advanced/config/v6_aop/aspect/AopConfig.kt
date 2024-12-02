package hello.advanced.config.v6_aop.aspect

import hello.advanced.config.AppConfigV1
import hello.advanced.config.AppConfigV2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(value = [AppConfigV1::class, AppConfigV2::class])
class AopConfig {
    @Bean
    fun logTraceAspect() = LogTraceAspect()
}