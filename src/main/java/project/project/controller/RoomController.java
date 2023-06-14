package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.domain.Room;

@Controller
@Slf4j
public class RoomController {

    @GetMapping("/room/registration")
    public String roomRegistrationForm(Model model){

        return "room/room_registration";

    }

    @PostMapping("/room/registration")
    public String roomRegistration(){

        return "redirect :/";
    }
}
