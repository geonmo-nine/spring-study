package hello.hellospring.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {
    @GetMapping("/hello")
    fun hello(model: Model): String {
        model.addAttribute("message", "Hello World!")
        return "hello"
    }

    @GetMapping("/hello/{name}")
    @ResponseBody
    fun hello2(@PathVariable name: String): Hello {
        return Hello(name)
    }

    data class Hello(val name: String)
}