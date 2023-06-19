package project.project.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.Room;
import project.project.domain.RoomInfo;
import project.project.domain.User;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.*;
import project.project.dto.RoomInfoRegistrationDto;
import project.project.dto.RoomRegistrationDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    void room_registrationTest(){

        //RoomInfo 생성
        List<Option> optionList=new ArrayList<>();
        optionList.add(Option.가스레인지);
        optionList.add(Option.냉장고);

        RoomInfoRegistrationDto roomInfoDto = new RoomInfoRegistrationDto(12.5, 20.21, "2", "5", Bearing.남,
                true, false, optionList);
        RoomInfo roomInfo = RoomInfo.makeRoomInfo(roomInfoDto);

        //Room 생성(Room 생성 시 내부에서 PhotoList 생성
        User user = new User("alstmd2126@naver.com", "강민승", "123");
        userRepository.save(user);
        List<MaintenanceItem> maintenanceItems=new ArrayList<>();
        maintenanceItems.add(MaintenanceItem.가스);
        maintenanceItems.add(MaintenanceItem.인터넷);

        RoomRegistrationDto roomDto = new RoomRegistrationDto(user.getId(), "기존 세입자", HouseType.단독, RoomType.원룸, 12312, "부천로 57번길 50", "201호"
                , "심곡동", 12.12121, 21.222, 500, 40, 5.0, LocalDate.now(), maintenanceItems, null,"제목","상세정보");

        String[] images = {"이미지1", "이미지2"};
        Room room = Room.makeRoom(roomDto, roomInfo, images, user);

        roomRepository.save(room);


        em.flush();
        em.clear();

        Room findRoom = roomRepository.findById(room.getId()).get();

        assertThat(room.getAddress()).isEqualTo(new Address(12312,"부천로 57번길 50","201호","심곡동"));
        assertThat(room.getRegistrant()).isEqualTo("기존 세입자");
        assertThat(room.getPhotos()).extracting("img").containsExactly("이미지1","이미지2");



    }

}