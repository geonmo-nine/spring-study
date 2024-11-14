package hello.advanced.app.v2

class OrderRepositoryV2 {
    fun save(itemId: String) {
        if (itemId == "ex") {
            throw IllegalStateException("exception error")
        }
        Thread.sleep(1000)
    }
}