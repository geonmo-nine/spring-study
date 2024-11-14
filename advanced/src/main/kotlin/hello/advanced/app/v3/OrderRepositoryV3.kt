package hello.advanced.app.v3

import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryV3 {
    fun save(itemId: String) {
        if (itemId == "ex") {
            throw IllegalStateException("exception error")
        }
        Thread.sleep(1000)
    }
}