package project.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.Room;
import project.project.dto.RoomInfoRegistrationDto;
import project.project.dto.RoomRegistrationDto;
import project.project.repository.RoomRepository;
import project.project.service.RoomService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;

    @GetMapping("/room/registration")
    public String roomRegistrationForm(Model model){
        model.addAttribute("roomRegistrationDto",new RoomRegistrationDto());
        model.addAttribute("roomInfoRegistrationDto",new RoomInfoRegistrationDto());
        return "room/room_registration";

    }

    @PostMapping("/room/registration")
    public String roomRegistration(@Valid RoomRegistrationDto roomDto,
                                   BindingResult roomResult,
                                   @Valid RoomInfoRegistrationDto roomInfoDto,
                                   BindingResult roomInfoResult,
                                   Model model){
        if(roomInfoResult.hasErrors() || roomResult.hasErrors()){

            return "room/room_registration";
        }

        roomService.roomRegistration(roomDto,roomInfoDto);


        return "redirect:/room/management";
    }

    @GetMapping("/room/management/{userId}")
    public String room_management(@PathVariable("userId")Long userId,Model model){
        List<Room> rooms = roomRepository.findRooms(userId);
        model.addAttribute("rooms",rooms);
        return "room/management";
    }
}
