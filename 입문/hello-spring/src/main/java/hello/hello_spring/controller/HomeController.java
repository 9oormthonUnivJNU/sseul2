package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // localhost 8080으로 들어오면 home()이 호출된다.
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
