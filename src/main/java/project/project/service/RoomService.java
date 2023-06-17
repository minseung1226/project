package project.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.project.dto.RoomInfoRegistrationDto;
import project.project.dto.RoomRegistrationDto;
import project.project.repository.RoomRepository;

@Service
@Slf4j
public class RoomService {
    RoomRepository roomRepository;

    public void roomRegistration(RoomRegistrationDto roomDto, RoomInfoRegistrationDto roomInfoDto){

    }
}
