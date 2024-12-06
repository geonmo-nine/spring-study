package advanced.aop.order.aop.exam.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Retry(val value: Int = 3)
