package advanced.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello-controller")
    String helloController() {
        System.out.println("hello-controller");
        return "hello-controller~~~~";
    }
}
