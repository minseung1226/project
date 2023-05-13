package project.project.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import project.project.controller.form.UserJoinForm;
import project.project.controller.form.UserLoginForm;
import project.project.domain.User;
import project.project.repository.UserRepository;
import project.project.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/join")
    @ResponseBody
    public String join(@Validated UserJoinForm form,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return bindingResult.getFieldErrors().get(0).getField();
        }
        boolean joinResult = userService.userJoin(form.getName(), form.getJoinEmail(), form.getJoinPw());
        if(joinResult){
            return "ok";
        }
        log.info("중복 이메일");
        return "duplicateEmail";
    }

    @ResponseBody
    @PostMapping("/login")
    public Boolean login(@Validated UserLoginForm userLoginForm, HttpSession session){
        User findUser = userRepository.findByEmail(userLoginForm.getLoginEmail());
        if(findUser==null||!findUser.getPw().equals(userLoginForm.getLoginPw())){
            return false;
        }

        session.setAttribute("user",findUser);

        return true;

    }
}
