package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello-controller")
    public String hello() {
        System.out.println("hello controller");
        return "Hello World";
    }
}
