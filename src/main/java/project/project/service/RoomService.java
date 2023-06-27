package project.project.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.Photo;
import project.project.domain.Room;
import project.project.domain.RoomInfo;
import project.project.domain.User;
import project.project.dto.room.RoomModifyDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.file.UploadFile;
import project.project.repository.roomrepository.DslRoomRepository;
import project.project.repository.roomrepository.RoomRepository;
import project.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final DslRoomRepository dslRoomRepository;
    private final EntityManager em;

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

    @Transactional
    public void roomDelete(Long roomId){
        Optional<Room> findRoomOptional = roomRepository.findById(roomId);
        if(findRoomOptional.isEmpty()){

        }
        Room findRoom = findRoomOptional.get();

        //로컬에있는 이미지 파일 삭제
        String[] images = findRoom.getPhotos().stream().map(Photo::getImg).toArray(String[]::new);
        for (String image : images) {
            UploadFile.fileDelete(image);
        }

        findRoom.delete();
        em.flush();
    }

    @Transactional
    public void roomUpdate(Long roomId, RoomModifyDto roomModifyDto){
        Room room = roomRepository.fetchFindById(roomId);

        List<String> delImgs = roomModifyDto.getDelImg();
        for (String delImg : delImgs) {
            UploadFile.fileDelete(delImg);
        }
        List<String> newImgNames = roomModifyDto.getNewImg().stream().map(multipartFile -> {
            UploadFile uploadFile = new UploadFile(multipartFile.getOriginalFilename());
            uploadFile.fileUpload(multipartFile);
            return uploadFile.getStoreName();
        }).collect(Collectors.toList());




        room.roomUpdate(roomModifyDto,newImgNames);


    }

}
