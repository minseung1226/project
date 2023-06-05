package project.project.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class RoomTest {
    @Autowired
    EntityManager em;

    @Test
    void RoomAndRoomInfoMakeTest(){
        List<MaintenanceList> maintenanceLists=new ArrayList<>();
        maintenanceLists.add(MaintenanceList.TV);
        maintenanceLists.add(MaintenanceList.전기세);

        List<Option> options=new ArrayList<>();
        options.add(Option.가스레인지);
        options.add(Option.냉장고);

        //User 생성
        User user = new User("alstmd", "강민승", "123", "971226"
                , "01055645417", new Address(12346,"부천시 심곡동 408-7", "308호","심곡동")
                ,"1912624",UserStatus.일반,UserJoinType.NORMAR);

        //RoomInfo 생성
        RoomInfo roomInfo = RoomInfo.makeRoomInfo(RoomType.원룸, 7.5, maintenanceLists,
                Bearing.남향, options, true, false, true, LocalDate.now());

        //Room 생성
        Address address = new Address(12345,"부천시 심곡동 408-7", "308호","심곡동");
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo("img1"));
        photos.add(new Photo("img2"));
        photos.add(new Photo("img3"));
        Room room = Room.makeRoom(address, user, roomInfo, photos, "img0", 300, 50, 23.42, "2층"
                , HouseType.원룸,Level.BRONZE);

        //User와 Room 저장
        em.persist(user);
        em.persist(room);

        em.flush();
        em.clear();

        Room findRoom = em.find(Room.class, room.getId());
        RoomInfo findRoomInfo = findRoom.getRoomInfo();
        User findUser = em.find(User.class, user.getId());

        //검증
        assertThat(findRoom).isEqualTo(room);
        assertThat(findUser).isEqualTo(user);
        assertThat(findRoomInfo).isEqualTo(roomInfo);


    }


}
