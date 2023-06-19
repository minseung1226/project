package project.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.project.repository.roomrepository.RoomRepository;

@Transactional
@SpringBootTest
public class CrudTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    RoomInfoRepository roomInfoRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    WishlistRepository wishlistRepository;

    @Test
    void userCrudTest(){

    }
}
