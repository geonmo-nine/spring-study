package hello.advanced.config.v4_postprocessor

import hello.advanced.config.AppConfigV1
import hello.advanced.config.AppConfigV2
import hello.advanced.config.v3_proxyfactory.advice.LogAdvice
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AppConfigV1::class, AppConfigV2::class)
class BeanPostProcessorConfig {
    @Bean
    fun logTraceProcessor() = LogTracePostProcessor("hello.advanced.app", getAdvisor())

    fun getAdvisor(): DefaultPointcutAdvisor {
        val pointcut = NameMatchMethodPointcut().apply { setMappedNames("request*", "save", "order*") }
        return DefaultPointcutAdvisor(pointcut, LogAdvice())
    }
}