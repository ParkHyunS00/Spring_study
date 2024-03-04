package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // Model에 데이터를 실어서 view로 넘길 수 있음

        model.addAttribute("data", "hello!!!");
        return "hello"; // resources/templates/hello.html -> Spring boot thymeleaf viewName 매핑, SSR 된 페이지 응답시
        // 정적인 콘텐츠는 static
    }
}
