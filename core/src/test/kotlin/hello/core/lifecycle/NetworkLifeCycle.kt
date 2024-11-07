package hello.core.lifecycle

class NetworkLifeCycle {
    private var url: String? = null

    init {
        println("생성자 호출 $url")
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun connect() {
        println("서비스 시작시 호출 $url")
    }

    fun call(message: String) {
        println("서비스를 호출함 $message")
    }

    fun init() {
        connect()
        call("최초 호출")
    }

    fun close() {
        disconnect()
    }

    fun disconnect() {
        println("연결종료")
    }
}