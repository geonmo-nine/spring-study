package hello.advanced.pureproxy.proxy

import hello.advanced.pureproxy.proxy.code.CacheProxy
import hello.advanced.pureproxy.proxy.code.ProxyPatternClient
import hello.advanced.pureproxy.proxy.code.RealSubject
import org.junit.jupiter.api.Test

class ProxyPatternTest {
    @Test
    fun noProxy() {
        val realSubject = RealSubject()
        val proxyPatternClient = ProxyPatternClient(realSubject)
        proxyPatternClient.execute()
        proxyPatternClient.execute()
        proxyPatternClient.execute()
    }

    @Test
    fun proxyTest() {
        val realSubject = RealSubject()
        val cacheProxy = CacheProxy(realSubject)
        val proxyPatternClient = ProxyPatternClient(cacheProxy)
        proxyPatternClient.execute()
        proxyPatternClient.execute()
        proxyPatternClient.execute()
    }
}