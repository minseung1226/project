package project.project.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.*;
import project.project.dto.RoomRegistrationDto;

import java.time.LocalDate;
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

        new RoomRegistrationDto();
    }


}
