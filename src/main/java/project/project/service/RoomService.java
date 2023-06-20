package project.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.Room;
import project.project.domain.RoomInfo;
import project.project.domain.User;
import project.project.dto.RoomInfoRegistrationDto;
import project.project.dto.RoomRegistrationDto;
import project.project.dto.RoomSimpleDto;
import project.project.file.UploadFile;
import project.project.repository.roomrepository.DslRoomRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final DslRoomRepository dslRoomRepository;

    @Transactional
    public void roomRegistration(RoomRegistrationDto roomDto, RoomInfoRegistrationDto roomInfoDto){
        //이미지 파일을 로컬에 저장후 저장된 이름반환
        String[] images= roomDto.getImg().stream().map(multipartFile -> {
            UploadFile uploadFile = new UploadFile(multipartFile.getOriginalFilename());
            uploadFile.fileUpload(multipartFile);
            return uploadFile.getStoreName();
        }).toArray(String[]::new);

        Optional<User> findOptionalUser = userRepository.findById(roomDto.getUserId());
        if(findOptionalUser.isEmpty()){

        }

        User findUser = findOptionalUser.get();
        RoomInfo roomInfo = RoomInfo.makeRoomInfo(roomInfoDto);

        Room room = Room.makeRoom(roomDto, roomInfo, images, findUser);

        roomRepository.save(room);


    }

}
