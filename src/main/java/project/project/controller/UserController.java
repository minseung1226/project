package project.project.controller;

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
import project.project.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping("/join")
    @ResponseBody
    public String join(@Validated UserJoinForm form,BindingResult bindingResult){
        log.info("컨트롤러까지는 왔어");
        if(bindingResult.hasErrors()){
            return bindingResult.getFieldErrors().get(0).getField();
        }
        log.info("검증통과");
        boolean joinResult = userService.userJoin(form.getName(), form.getJoinEmail(), form.getJoinPw());
        if(joinResult){
            log.info("가입완료");
            return "ok";
        }
        log.info("중복 이메일");
        return "duplicateEmail";
    }
}
