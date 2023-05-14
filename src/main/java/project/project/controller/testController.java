package project.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.controller.form.UserJoinForm;

@Controller
public class testController {

    @GetMapping("/")
    public String test(HttpSession session)
    {
        return "home";
    }


}
