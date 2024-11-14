package hello.advanced.pureproxy.concreteproxy.code

class ConcreteClient(
    val concreteLogic: ConcreteLogic
) {
    fun execute() {
        concreteLogic.operate()
    }
}