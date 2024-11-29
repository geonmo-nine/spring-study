package hello.advanced.proxyfactory

import hello.advanced.common.advice.TimeAdvice
import hello.advanced.common.service.ConcreteService
import hello.advanced.common.service.ServiceImpl
import hello.advanced.common.service.ServiceInterface
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.aop.ClassFilter
import org.springframework.aop.MethodMatcher
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.AopUtils
import org.springframework.aop.support.DefaultPointcutAdvisor
import java.lang.reflect.Method
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProxyFactoryTest {
    private val logger = KotlinLogging.logger { }

    @Test
    fun `인터페이스가 있으면 jdk동적 프록시를 사용`() {
        val serviceImpl = ServiceImpl()
        val proxyFactory = ProxyFactory(serviceImpl)
        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ServiceInterface
        proxy.save()

        assertTrue(AopUtils.isAopProxy(proxy))
        assertTrue(AopUtils.isJdkDynamicProxy(proxy))
        assertFalse(AopUtils.isCglibProxy(proxy))

        logger.info { proxy.javaClass }
    }

    @Test
    fun `어드바이저를 사용해서 적용`() {
        val serviceImpl = ConcreteService()
        val proxyFactory = ProxyFactory(serviceImpl)

        val advisor = DefaultPointcutAdvisor(Pointcut.TRUE, TimeAdvice())

        proxyFactory.addAdvisor(advisor)
        val proxy = proxyFactory.proxy as ConcreteService
        proxy.call()

        assertTrue(AopUtils.isAopProxy(proxy))
        assertTrue(AopUtils.isCglibProxy(proxy))
        assertFalse(AopUtils.isJdkDynamicProxy(proxy))

        logger.info { proxy.javaClass }
    }

    @Test
    fun `직접 만든 포인트 컷의 어드바이저를 사용해서 적용`() {
        val serviceImpl = ServiceImpl()
        val proxyFactory = ProxyFactory(serviceImpl)

        val advisor = DefaultPointcutAdvisor(MyPointCut(), TimeAdvice())

        proxyFactory.addAdvisor(advisor)
        val proxy = proxyFactory.proxy as ServiceInterface
        proxy.save()
        proxy.find()
    }

    class MyPointCut : Pointcut {
        override fun getClassFilter(): ClassFilter {
            return ClassFilter.TRUE
        }

        override fun getMethodMatcher(): MethodMatcher {
            return MyMethodMatcher()
        }
    }

    class MyMethodMatcher : MethodMatcher {
        companion object {
            val MATCHER_REGEX = Regex("save")
        }

        private val logger = KotlinLogging.logger { }
        override fun matches(method: Method, targetClass: Class<*>): Boolean {
            logger.info { "method.name=${method.name} targetClass=${targetClass}" }
            return method.name.matches(MATCHER_REGEX)
        }

        override fun matches(method: Method, targetClass: Class<*>, vararg args: Any?): Boolean {
            TODO("Not yet implemented")
        }

        override fun isRuntime(): Boolean {
            return false
        }
    }
}