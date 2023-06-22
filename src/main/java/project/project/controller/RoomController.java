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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.dto.room.RoomSimpleDto;
import project.project.repository.roomrepository.DslRoomRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.service.RoomService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final DslRoomRepository dslRoomRepository;

    @GetMapping("/room/registration")
    public String roomRegistrationForm(Model model){
        model.addAttribute("roomRegistrationDto",new RoomRegistrationDto());
        model.addAttribute("roomInfoRegistrationDto",new RoomInfoRegistrationDto());
        return "room/registration";

    }

    @PostMapping("/room/registration")
    public String roomRegistration(@Valid RoomRegistrationDto roomDto,
                                   BindingResult roomResult,
                                   @Valid RoomInfoRegistrationDto roomInfoDto,
                                   BindingResult roomInfoResult,
                                   RedirectAttributes redirectAttributes){
        if(roomInfoResult.hasErrors() || roomResult.hasErrors()){

            List<FieldError> fieldErrors = roomResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                log.info("room={}",fieldError.toString());
            }
            List<FieldError> fieldErrors1 = roomInfoResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors1) {
                log.info("roomInfo={}",fieldError.toString());
            }

            return "room/registration";
        }

        roomService.roomRegistration(roomDto,roomInfoDto);

        redirectAttributes.addAttribute("id",roomDto.getUserId());

        return "redirect:/room/management/{id}";
    }

    @GetMapping("/room/management/{userId}")
    public String room_management(@PathVariable("userId")Long userId,Model model){
        List<RoomSimpleDto> rooms = dslRoomRepository.findRooms(userId);
        model.addAttribute("roomDtos",rooms);

        return "room/management";
    }

    @GetMapping("/room/detailInfo/{id}")
    public String room_detail_info(@PathVariable("id")Long roomId,Model model){

        return "room/detail_info";
    }
}
