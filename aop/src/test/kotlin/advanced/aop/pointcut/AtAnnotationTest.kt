package advanced.aop.pointcut

import advanced.aop.order.aop.member.MemberServiceImpl
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.aop.Pointcut
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import

@Import(AtAnnotationTest.Aop::class)
@SpringBootTest
class AtAnnotationTest {
    @Autowired
    private lateinit var memberServiceImpl: MemberServiceImpl

    @Test
    fun atAnnotationTest() {
        val method = MemberServiceImpl::class.java.getMethod("hello", String::class.java)
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = "@annotation(advanced.aop.order.aop.member.annotation.MethodAop)"
        assertThat(pointcut.matches(method, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun atAnnotationTest2() {
        memberServiceImpl.hello("adfs")
    }

    @Aspect
    class Aop {
        @Around("@annotation(advanced.aop.order.aop.member.annotation.MethodAop)")
        fun atAnnotation(joinPoint: ProceedingJoinPoint): Any? {
            println("AtAnnotationTest2~")
            return joinPoint.proceed()
        }
    }
}