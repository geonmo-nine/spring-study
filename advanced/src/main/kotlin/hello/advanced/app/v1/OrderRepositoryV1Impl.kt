package hello.advanced.app.v1

open class OrderRepositoryV1Impl : OrderRepositoryV1 {
    override fun save(itemId: String) {
        if (itemId == "ex") {
            throw IllegalStateException("exception error")
        }
        Thread.sleep(1000)
    }
}