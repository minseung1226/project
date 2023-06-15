package project.project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.controller.form.room.RoomInfoRegistrationForm;
import project.project.controller.form.room.RoomRegistrationForm;
import project.project.domain.Room;

import java.util.List;

@Controller
@Slf4j
public class RoomController {

    @GetMapping("/room/registration")
    public String roomRegistrationForm(Model model){
        model.addAttribute("roomRegistrationForm",new RoomRegistrationForm());
        model.addAttribute("roomInfoForm",new RoomInfoRegistrationForm());
        return "room/room_registration";

    }

    @PostMapping("/room/registration")
    public String roomRegistration(@Valid RoomRegistrationForm roomForm, BindingResult roomResult,
                                   @Valid RoomInfoRegistrationForm roomInfoForm, BindingResult roomInfoResult){
        if(roomInfoResult.hasErrors() || roomResult.hasErrors()){

            return "room/room_registration";
        }

        return "redirect :/";
    }
}
