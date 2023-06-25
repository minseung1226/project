package project.project;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.*;
import project.project.domain.enum_type.*;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.repository.InquiryRepository;
import project.project.repository.UserRepository;
import project.project.repository.WishlistRepository;
import project.project.repository.roomrepository.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;
    private final WishlistRepository wishlistRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData(){

        //RoomInfo 생성
        List<Option> options=new ArrayList<>();
        options.add(Option.냉장고);
        options.add(Option.가스레인지);
        RoomInfoRegistrationDto roomInfoDto = new RoomInfoRegistrationDto(20.0, 30.0, "2", "3", Bearing.남, true, false, options);
        RoomInfo roomInfo = RoomInfo.makeRoomInfo(roomInfoDto);

        //User 생성 후 저장
        User user = new User("alstmd1226@naver.com", "강민준", "123");
        userRepository.save(user);

        List<MaintenanceItem> maintenanceItems=new ArrayList<>();
        maintenanceItems.add(MaintenanceItem.가스);
        maintenanceItems.add(MaintenanceItem.전기);

        RoomRegistrationDto roomDto = new RoomRegistrationDto(user.getId(), "기존 세입자", HouseType.단독, RoomType.원룸,
                12312, "부천로 57번길 50", "201호", "심곡동", 23.12312, 30.123123, 500, 40, 5.0, LocalDate.now(), maintenanceItems
                , null, "부천역 10분거리 복층형 원룸", "오세요 오세요 오세요");

        Room room = Room.makeRoom(roomDto, roomInfo, new String[]{"59281079-6389-445a-9296-82c3d0a54443.jpg", "8b961600-b9d3-4880-9ddd-c63c3e4e964f.png","8c4e9d51-d09d-47ab-a3cd-4a2f0d737366.jpg","7a3daca6-e95b-42e0-a9c3-48e856fcf31c.png"}, user);
        roomRepository.save(room);

        //WishList 저장
        Wishlist wishlist1 = new Wishlist(user,room);
        Wishlist wishlist2 = new Wishlist(user, room);
        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);

        //Inquiry 저장
        Inquiry inquiry1 = new Inquiry(user, room);
        Inquiry inquiry2 = new Inquiry(user, room);
        Inquiry inquiry3 = new Inquiry(user, room);
        inquiryRepository.save(inquiry1);
        inquiryRepository.save(inquiry2);
        inquiryRepository.save(inquiry3);

    }
}
