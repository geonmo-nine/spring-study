package advanced.aop.pointcut

import advanced.aop.order.aop.member.MemberService
import advanced.aop.order.aop.member.annotation.MethodAop
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import kotlin.test.Test

@Import(ParameterTest.ArgAop::class)
@SpringBootTest
class ParameterTest {
    @Autowired
    private lateinit var memberService: MemberService

    @Test
    fun arsTset() {
        memberService.hello("1231")
    }

    @Aspect
    class ArgAop {
        @Pointcut("execution(* advanced.aop.order.aop.member..*(..))")
        fun allMember() {
        }

        @Around("allMember()")
        fun getArg1(joinPoint: ProceedingJoinPoint): Any? {
            val first = joinPoint.args.first()
            println("getArg1 $first")
            return joinPoint.proceed()
        }

        @Around("allMember() && args(arg,..)")
        fun getArg2(joinPoint: ProceedingJoinPoint, arg: String): Any? {
            println("getArg2 $arg")
            return joinPoint.proceed()
        }

        @Before("allMember() && args(arg,..)")
        fun getArg3(arg: String) {
            println("getArg3 $arg")
        }

        @Before("allMember() && @annotation(annotation)")
        fun getArg4(annotation: MethodAop) {
            println("getArg4 ${annotation.value}")
        }
    }
}