package advanced.aop.pointcut

import advanced.aop.order.aop.member.MemberServiceImpl
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import java.lang.reflect.Method

class ExecuteTest {
    val pointcut = AspectJExpressionPointcut()
    lateinit var helloMethod: Method

    @BeforeEach
    fun setUp() {
        helloMethod = MemberServiceImpl::class.java.getMethod("hello", String::class.java)
    }

    @Test
    fun print() {
        // public java.lang.String advanced.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        println(helloMethod)
    }

    @Test
    fun exactMatch() {
        pointcut.expression = "execution(public int advanced.aop.order.aop.member.MemberServiceImpl.hello(String))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun allMatch() {
        pointcut.expression = "execution(* *(..))" // 반환타입 매서드 인자개수 (가장 축약한 타입)
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun packageExactFail() {
        pointcut.expression = "execution(* advanced.aop.order.aop.*.*(..))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isFalse()
    }

    @Test
    fun packageExactSuccess() {
        pointcut.expression = "execution(* advanced.aop.order.aop..*.*(..))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun typeExactMatch() {
        pointcut.expression = "execution(* advanced.aop.order.aop.member.MemberServiceImpl.*(..))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun typeSuperMatch() {
        pointcut.expression = "execution(* advanced.aop.order.aop.member.MemberService.*(..))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }

    @Test
    fun typeSuperButNotMatch() {
        pointcut.expression = "execution(* advanced.aop.order.aop.member.MemberService.*(..))"
        val declaredMethod = MemberServiceImpl::class.java.getMethod("internal")
        // 부모타입(인터페이스)에 있는 매서드만 매칭된다
        assertThat(pointcut.matches(declaredMethod, MemberServiceImpl::class.java)).isFalse()
    }

    @Test
    fun argsMatch() {
        pointcut.expression = "execution(* advanced.aop.order.aop.member.MemberService.*(String, ..))"
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl::class.java)).isTrue()
    }
}