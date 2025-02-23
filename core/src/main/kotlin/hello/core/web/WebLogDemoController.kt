package hello.core.web

import hello.core.common.MyLogger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WebLogDemoController(
    private val myLogger: MyLogger,
    private val myLoggerService: MyLoggerService
) {
    @GetMapping("/log-demo")
    @ResponseBody
    fun logDemo(request: HttpServletRequest) {
        myLogger.requestUrl = request.requestURL.toString()
        myLogger.log("controller test")
        myLoggerService.loggerService()
    }
}