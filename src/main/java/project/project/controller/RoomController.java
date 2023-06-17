package project.project.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import project.project.dto.RoomInfoRegistrationDto;
import project.project.dto.RoomRegistrationDto;

import java.util.List;

@Controller
@Slf4j
public class RoomController {

    @GetMapping("/room/registration")
    public String roomRegistrationForm(Model model){
        model.addAttribute("roomRegistrationDto",new RoomRegistrationDto());
        model.addAttribute("roomInfoRegistrationDto",new RoomInfoRegistrationDto());
        return "room/room_registration";

    }

    @PostMapping("/room/registration")
    public String roomRegistration(@Valid RoomRegistrationDto roomForm,
                                   BindingResult roomResult,
                                   @Valid RoomInfoRegistrationDto roomInfoForm,
                                   BindingResult roomInfoResult,
                                   Model model){
        if(roomInfoResult.hasErrors() || roomResult.hasErrors()){

            return "room/room_registration";
        }

        return "redirect :/";
    }
}
