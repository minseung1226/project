package project.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.controller.form.room.RoomDetailForm;
import project.project.controller.form.room.RoomMapForm;
import project.project.domain.Room;
import project.project.dto.room.RoomModifyDto;
import project.project.dto.roominfo.RoomInfoModifyDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.dto.room.RoomSimpleDto;
import project.project.repository.roomrepository.DslRoomRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.search.RoomSearchParameters;
import project.project.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

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
            return "room/registration";
        }

        roomService.roomRegistration(roomDto,roomInfoDto);

        redirectAttributes.addAttribute("userId",roomDto.getUserId());

        return "redirect:/room/management/{userId}";
    }

    @GetMapping("/room/management/{userId}")
    public String roomManagement(@PathVariable("userId")Long userId,Model model){
        List<RoomSimpleDto> rooms = dslRoomRepository.findRoomDtos(userId);
        model.addAttribute("roomDtos",rooms);

        return "room/management/management";
    }

    @GetMapping("/room/management/detailInfo/{roomId}")
    public String roomDetailInfo(@PathVariable("roomId")Long roomId,Model model){

        RoomModifyDto roomDto = dslRoomRepository.findRoomDto(roomId);
        model.addAttribute("roomModifyDto",roomDto);
        return "room/management/detail_info";
    }

    @PostMapping("/room/management/delete/{roomId}")
    public String deleteRoom(@PathVariable("roomId")Long roomId,Long userId,RedirectAttributes redirectAttributes){
        roomService.roomDelete(roomId);
        redirectAttributes.addAttribute("userId",userId);

        return "redirect:/room/management/{userId}";
    }

    @GetMapping("/room/management/modify/{roomId}")
    public String modifyRoom(@PathVariable("roomId")Long roomId,Long userId,Model model,RedirectAttributes redirectAttributes){
        RoomModifyDto roomDto = dslRoomRepository.findRoomDto(roomId);

        model.addAttribute("roomModifyDto",roomDto  );
        model.addAttribute("roomInfoModifyDto",roomDto.getRoomInfoModifyDto());

        redirectAttributes.addAttribute("userId",userId);
        return "room/management/modify";
    }

    @PostMapping("/room/modify/{roomId}")
    public String modify(@PathVariable("roomId")Long roomId,
                         @Valid RoomModifyDto roomModifyDto,BindingResult roomDtoBindingResult,
                         @Valid RoomInfoModifyDto roomInfoModifyDto,BindingResult roomInfoDtoBindingResult,
                         RedirectAttributes redirectAttributes){
        if(roomDtoBindingResult.hasErrors()||roomInfoDtoBindingResult.hasErrors()){
            return "room/modify";
        }
        roomModifyDto.setRoomInfoModifyDto(roomInfoModifyDto);

        roomService.roomUpdate(roomId,roomModifyDto);

        redirectAttributes.addAttribute("roomId",roomId);
        return "redirect:/room/detailInfo/{roomId}";
    }

    @GetMapping("/room/roomList")
    @ResponseBody
    public List<RoomMapForm> roomFormList(RoomSearchParameters roomSearch){
        List<Room> roomList = dslRoomRepository.roomSearch(roomSearch);

        List<RoomMapForm> roomMapForms = roomList.stream().map(room -> new RoomMapForm(
                        room.getId(),
                        room.getPhotos().get(0).getImg(),
                        room.getRoomInfo().getRealSize(),
                        room.getLat(),
                        room.getLng(),
                        room.getTitle(),
                        room.getRoomType(),
                        room.getDeposit(),
                        room.getMonthlyRent(),
                        room.getMaintenance()))
                .collect(Collectors.toList());

        return roomMapForms;
    }
    @GetMapping("/room/detail/{roomId}")
    public String roomDetail(@PathVariable("roomId")Long roomId,Model model){
        Room room = roomRepository.fetchFindById(roomId);
        RoomDetailForm roomDetailForm = new RoomDetailForm(room);
        model.addAttribute("roomDetailForm",roomDetailForm);
        log.info("room={}",roomDetailForm);
        return "room/detail_view";
    }

    @ResponseBody
    @PostMapping("/inquiry/save")
    public String inquiry(Long roomId,Long userId){

        return roomService.inquiry(roomId,userId);
    }
    @ResponseBody
    @PostMapping("/wishlist/save")
    public String wishlist(Long roomId,Long userId){
        roomService.wishlistSave(roomId,userId);

        return "success";
    }

}
