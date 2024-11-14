package hello.advanced.jdkdynamic

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import java.lang.reflect.Method
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions

class ReflectionTest {
    private val logger = KotlinLogging.logger {}

    @Test
    fun reflection0() {
        val target = Reflection()
        logger.info { "start" }
        target.callA()
        logger.info { "end" }

        logger.info { "start" }
        target.callB()
        logger.info { "end" }
    }

    @Test
    fun reflection1() {
        logger.info { "start" }
        val forName = Class.forName("hello.advanced.jdkdynamic.ReflectionTest\$Reflection")
        val method = forName.getMethod("callA")
        method.invoke(Reflection())
        logger.info { "end" }
    }

    @Test
    fun reflection2() {
        val target = Reflection()
        val classInfo = Class.forName("hello.advanced.jdkdynamic.ReflectionTest\$Reflection")

        val callA = classInfo.getMethod("callA")
        dynamicCall(callA, target)

        val callB = classInfo.getMethod("callB")
        dynamicCall(callB, target)
    }

    fun dynamicCall(method: Method, target: Any) {
        logger.info { "start" }
        method.invoke(target)
        logger.info { "end" }
    }

    @Test
    fun reflection3() {
        val kClass = Reflection::class
        val method = kClass.functions.find { it.name == "callA" } as KFunction<*>
        method.call(Reflection())
    }


    class Reflection {
        fun callA() {
            println("callA~")
        }

        fun callB() {
            println("callB~")
        }
    }
}