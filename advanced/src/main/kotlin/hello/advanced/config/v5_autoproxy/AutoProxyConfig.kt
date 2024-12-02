package hello.advanced.config.v5_autoproxy

import hello.advanced.config.AppConfigV1
import hello.advanced.config.AppConfigV2
import hello.advanced.config.v3_proxyfactory.advice.LogAdvice
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AppConfigV1::class, AppConfigV2::class)
class AutoProxyConfig {
//    @Bean
//    fun advisor1(): DefaultPointcutAdvisor {
//        val pointcut = NameMatchMethodPointcut().apply { setMappedNames("request*", "save", "order*") }
//        return DefaultPointcutAdvisor(pointcut, LogAdvice())
//    }

    @Bean
    fun advisor2(): DefaultPointcutAdvisor {
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression =
            "execution(* hello.advanced.app.v1..*(..)) && !execution(* hello.advanced.app.v1..noLog(..))"
        return DefaultPointcutAdvisor(pointcut, LogAdvice())
    }
}