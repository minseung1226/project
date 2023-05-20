package project.project.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.project.controller.form.UserJoinForm;
import project.project.controller.form.UserLoginForm;
import project.project.domain.User;
import project.project.domain.enum_type.UserJoinType;
import project.project.repository.UserRepository;
import project.project.service.KakaoService;
import project.project.service.UserService;

import java.util.List;

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
        boolean joinResult = userService.userJoin(form.getName(), form.getJoinEmail(), form.getJoinPw(), UserJoinType.NORMAR);
        if(joinResult){
            return "ok";
        }
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

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/kakao/login")
    public String kakao_login(String code){


        /*log.info("code={}",code);
        String kakaoToken = kakaoService.getKakaoToken(code);
        JSONObject jsonObject = new JSONObject(kakaoToken);
        String accessToken = jsonObject.get("access_token").toString();
        log.info("access_token={}",accessToken);

        String userInfo = kakaoService.getUserInfo(accessToken);
        JSONObject userInfoJson = new JSONObject(userInfo);
        log.info("userInfoJson={}",userInfoJson.toString());
        JSONObject kakaoAccountJson = (JSONObject) userInfoJson.get("kakao_account");
        log.info(String.valueOf(kakaoAccountJson));
        String email = kakaoAccountJson.get("email").toString();
        log.info("email={}",email);*/
        return "redirect:/";
    }
}
