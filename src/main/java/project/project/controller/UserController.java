package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.project.controller.form.user.UserJoinForm;
import project.project.controller.form.user.UserLoginForm;
import project.project.controller.form.user.UserModifyForm;
import project.project.domain.User;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;
import project.project.file.UploadFile;
import project.project.repository.UserRepository;
import project.project.service.KakaoService;
import project.project.service.UserService;
import retrofit2.http.Path;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.Random;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    private final DefaultMessageService messageService;


    public UserController(UserService userService, KakaoService kakaoService, UserRepository userRepository) {
        this.userService = userService;
        this.kakaoService = kakaoService;
        this.userRepository = userRepository;
        this.messageService= NurigoApp.INSTANCE.initialize("NCSDPSCNG9CQLBVW","NOT2VRY1CMXTDP6T24N0YDYOMJMPCEGG","https://api.coolsms.co.kr");
    }


    //회원가입
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


    //로그인
    @ResponseBody
    @PostMapping("/login")
    public Boolean login(@Validated UserLoginForm userLoginForm, HttpSession session){
        User findUser = userRepository.findNormalByEmail(userLoginForm.getLoginEmail(),UserJoinType.NORMAR);
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

    //카카오 로그인
    @GetMapping("/kakao/login")
    public String kakao_login(String code,HttpSession session){
        Long id = kakaoService.kakaoLogin(code);
        session.setAttribute("user",id);
        return "redirect:/";
    }

    //계정정보 페이지 이동
    @GetMapping("/mypage/account")
    public String mypage(HttpSession session, Model model){
        Object id = session.getAttribute("user");
        Optional<User> findUser = userRepository.findById((Long) id);
        User user = findUser.get();

        if(user.getAddress()==null) {
            user.changeAddress(new Address(null, null, null, null));
        }
        model.addAttribute("user", user);
        return "mypage/account";
    }

    //휴대폰 인증
    @ResponseBody
    @PostMapping("/user/send_sms")
    public String sendMessage(String tel){
        Random random=new Random();
        String number="";
        for(int i=0;i<4;i++){
            number+=random.nextInt(10);
        }
        log.info("인증번호= {}"+number);
        /*Message message=new Message();
        message.setFrom("01055645417");
        message.setTo(tel);
        message.setText("인증번호는 "+number+" 입니다.");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
*/

        return number;
    }

    //비밀번호 변경
    @PostMapping("/user/pw_change")
    public String pw_change(String pw,HttpSession session){
        userService.pwChange((Long) session.getAttribute("user"),pw);
        return "redirect:/mypage/account";
    }

    @ResponseBody
    @GetMapping("/images/{pimg}")
    public Resource profileImage(@PathVariable String pimg) throws MalformedURLException {
        return new UrlResource("file:"+ UploadFile.PATH+    pimg);
    }

    @PostMapping("/user/modify")
    public String userModify(UserModifyForm form){

        userService.profileModify(form.getId(),form.getName(),form.getTel(),form.getEmail(),
                form.getPimg(),form.getPostcode(),form.getAddress(),form.getDetailAddress(),
                form.getExtraAddress());

        return "redirect:/mypage/account";
    }

    @ResponseBody
    @GetMapping("/user/registration/check/{userId}")
    public boolean checkRoomRegistration(@PathVariable("userId") Long userId){
        System.out.println("왔다. userId="+userId);
        Boolean result = userService.checkRoomRegistrationEligibility(userId);
        System.out.println("왔다. result="+result);

        return result;
    }


}
