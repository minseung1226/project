package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project.domain.Room;

@Controller
@Slf4j
public class RoomController {

    @GetMapping("/room/registration")
    public String roomRegistration(Model model){

        return "room/room_registration";

    }
}
