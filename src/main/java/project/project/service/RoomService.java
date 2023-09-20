package project.project.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.project.domain.*;
import project.project.dto.room.RoomModifyDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;
import project.project.dto.room.RoomRegistrationDto;
import project.project.file.UploadFile;
import project.project.repository.InquiryRepository;
import project.project.repository.WishlistRepository;
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
    private final InquiryRepository inquiryRepository;
    private final WishlistRepository wishlistRepository;

    private final DslRoomRepository dslRoomRepository;
    private DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSDPSCNG9CQLBVW","NOT2VRY1CMXTDP6T24N0YDYOMJMPCEGG","https://api.coolsms.co.kr");
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

    @Transactional
    public String inquiry(Long roomId,Long userId){
        User user = userRepository.findById(userId).get();
        Room room = roomRepository.fetchFindById(roomId);


        if(!StringUtils.hasText(user.getPhone())){
            return "phone_number_missing";
        }

        Inquiry inquiry = new Inquiry(user, room);

        inquiryRepository.save(inquiry);

/*
        Message message=new Message();
        message.setFrom(user.getPhone());
        message.setTo(room.getUser().getPhone());
        message.setText("매물번호 : "+room.getRoomNumber()+
                        " 주소 : "+room.getAddress().getAddress()+room.getAddress().getDetailAddress()+
                        " 해당 매물 문의 드립니다.");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
*/

        return "success";
    }
    @Transactional
    public void wishlistSave(Long roomId,Long userId){
        Room room = roomRepository.findById(roomId).get();
        User user = userRepository.findById(userId).get();

        Wishlist wishlist = new Wishlist(user, room);

        wishlistRepository.save(wishlist);
    }



}
