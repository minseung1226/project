package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.project.controller.form.UserJoinForm;
import project.project.controller.form.UserLoginForm;
import project.project.domain.User;
import project.project.domain.enum_type.UserJoinType;
import project.project.repository.UserRepository;
import project.project.service.KakaoService;
import project.project.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;
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
        return "duplicateEmail";
    }

    @ResponseBody
    @PostMapping("/login")
    public Boolean login(@Validated UserLoginForm userLoginForm, HttpSession session){
        User findUser = userRepository.findByEmail(userLoginForm.getLoginEmail(),UserJoinType.NORMAR);
        if(findUser==null||!findUser.getPw().equals(userLoginForm.getLoginPw())){
            return false;
        }
        session.setAttribute("user",findUser.getId());
        return true;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/kakao/login")
    public String kakao_login(String code,HttpSession session){
        Long id = kakaoService.kakaoLogin(code);
        session.setAttribute("user",id);
        return "redirect:/";
    }

    @GetMapping("/user/mypage")
    public String mypage(HttpSession session, Model model){
        Object id = session.getAttribute("user");
        Optional<User> findUser = userRepository.findById((Long) id);
        model.addAttribute("user",findUser.get());

        return "mypage/mypage";
    }
}
