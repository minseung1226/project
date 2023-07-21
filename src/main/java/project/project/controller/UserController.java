package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.controller.form.room.RoomInquiryForm;
import project.project.controller.form.room.RoomMapForm;
import project.project.controller.form.room.RoomWishlistForm;
import project.project.controller.form.user.UserJoinForm;
import project.project.controller.form.user.UserLoginForm;
import project.project.controller.form.user.UserModifyForm;
import project.project.domain.Inquiry;
import project.project.domain.Room;
import project.project.domain.User;
import project.project.domain.Wishlist;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;
import project.project.file.UploadFile;
import project.project.repository.InquiryRepository;
import project.project.repository.UserRepository;
import project.project.repository.WishlistRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.service.KakaoService;
import project.project.service.UserService;
import retrofit2.http.Path;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor()
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    private final DefaultMessageService messageService=NurigoApp.INSTANCE.initialize("NCSDPSCNG9CQLBVW","NOT2VRY1CMXTDP6T24N0YDYOMJMPCEGG","https://api.coolsms.co.kr");

    private final WishlistRepository wishlistRepository;
    private final InquiryRepository inquiryRepository;




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
    @GetMapping("/mypage/account/{userId}")
    public String mypage(@PathVariable("userId")Long userId, Model model){

        Optional<User> findUser = userRepository.findById(userId);
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
    public String pw_change(String pw, HttpSession session, RedirectAttributes redirectAttributes){
        Long userId = (Long) session.getAttribute("user");
        userService.pwChange(userId,pw);
        redirectAttributes.addAttribute("userId",userId);
        return "redirect:/mypage/account/{userId}";
    }

    @ResponseBody
    @GetMapping("/images/{pimg}")
    public Resource profileImage(@PathVariable String pimg) throws MalformedURLException {
        return new UrlResource("file:"+ UploadFile.PATH+    pimg);
    }

    @PostMapping("/user/modify")
    public String userModify(UserModifyForm form,RedirectAttributes redirectAttributes){

        userService.profileModify(form.getId(),form.getName(),form.getTel(),form.getEmail(),
                form.getPimg(),form.getPostcode(),form.getAddress(),form.getDetailAddress(),
                form.getExtraAddress());

        redirectAttributes.addAttribute("userId",form.getId());

        return "redirect:/mypage/account/{userId}";
    }

    @ResponseBody
    @GetMapping("/user/registration/check/{userId}")
    public boolean checkRoomRegistration(@PathVariable("userId") Long userId){
        Boolean result = userService.checkRoomRegistrationEligibility(userId);

        return result;
    }

    @GetMapping("/mypage/wishlist/{userId}")
    public String wishlist(@PathVariable("userId")Long userId,Model model){

        List<Wishlist> list = wishlistRepository.findWishlistsByUserId(userId);

        List<RoomWishlistForm> wishlistForms = list.stream().map(wishlist -> new RoomWishlistForm(
                wishlist.getId(),
                wishlist.getRoom().getId(),
                wishlist.getRoom().getPhotos().get(0).getImg(),
                wishlist.getRoom().getRoomInfo().getRealSize(),
                wishlist.getRoom().getTitle(),
                wishlist.getRoom().getRoomType(),
                wishlist.getRoom().getDeposit(),
                wishlist.getRoom().getMonthlyRent(),
                wishlist.getRoom().getMaintenance()
        )).collect(Collectors.toList());


        model.addAttribute("wishlistForms",wishlistForms);

        return "mypage/wishlist";
    }


    @PostMapping("/wishlist/delete")
    public String wishlistDelete(Long[] wishlistIds,Long userId,RedirectAttributes redirectAttributes){

        List<Long> list = Arrays.stream(wishlistIds).collect(Collectors.toList());

        wishlistRepository.bulkDeleteByIds(list);
        redirectAttributes.addAttribute("userId",userId);
        return "redirect:/mypage/wishlist/{userId}";
    }

    @GetMapping("/mypage/inquiry/{userId}")
    public String inquiryList(@PathVariable("userId")Long userId,Model model){
        List<Inquiry> inquirys = inquiryRepository.findInquirysByUserId(userId);
        List<RoomInquiryForm> inquiryForms = inquirys.stream().map(inquiry ->
                new RoomInquiryForm(
                        inquiry.getId(),
                        inquiry.getRoom().getId(),
                        inquiry.getRoom().getPhotos().get(0).getImg(),
                        inquiry.getRoom().getRoomInfo().getRealSize(),
                        inquiry.getRoom().getTitle(),
                        inquiry.getRoom().getRoomType(),
                        inquiry.getRoom().getDeposit(),
                        inquiry.getRoom().getMonthlyRent(),
                        inquiry.getRoom().getMaintenance())
        ).collect(Collectors.toList());
        model.addAttribute("inquiryForms",inquiryForms);


        return "mypage/inquiry";
    }

    @PostMapping("/inquiry/delete")
    public String inquiryDelete(Long[] inquiryIds,Long userId,RedirectAttributes redirectAttributes){
        List<Long> list = Arrays.stream(inquiryIds).collect(Collectors.toList());
        inquiryRepository.bulkDeleteByIds(list);

        redirectAttributes.addAttribute("userId",userId);

        return "redirect:/mypage/inquiry/{userId}";


    }



}
