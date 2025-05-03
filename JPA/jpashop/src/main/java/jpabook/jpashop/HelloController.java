package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // Model: 스프링 UI, 컨트롤러에서 모델에 데이터를 실어서 뷰로 넘길 수 있다.
    @GetMapping("hello")
    public String Hello(Model model){
        model.addAttribute("data", "hello!!!");
        return "hello"; //return은 화면 이름을 리턴
    }
}
