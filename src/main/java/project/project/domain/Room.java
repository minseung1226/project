package project.project.domain;

import jakarta.persistence.*;
import lombok.*;
import project.project.domain.baseentity.BaseEntity;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.*;
import project.project.dto.room.RoomModifyDto;
import project.project.dto.room.RoomRegistrationDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @Embedded
    private Address address;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "room_info_id")
    private RoomInfo roomInfo;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Photo> photos=new ArrayList<>();

    private String registrant; // 등록자 정보 : 세입자,임대인
    private double lat; //위도
    private double lng;  //경도


    private Integer deposit; //보증금
    private Integer monthlyRent; //월세



    @Enumerated(EnumType.STRING)
    private HouseType houseType; //건물 종류

    @Enumerated(EnumType.STRING)
    private RoomType roomType; //집 종류


    private double maintenance; // 관리비
    private LocalDate moveInDate; // 입주일


    @Convert(converter = EnumListConverter.class)
    @Enumerated(EnumType.STRING)
    private List<MaintenanceItem> maintenanceItem=new ArrayList<>(); //관리비포함항목

    private String title; //제목
    private String content; //상세정보

    @Column(unique = true)
    private String roomNumber; //매물 번호

    @Enumerated(EnumType.STRING)
    private RoomStatus status=RoomStatus.진행중;

    private EntityStatus entityStatus=EntityStatus.BASIC;

    @PrePersist
    private void generateNumber(){

        // roomNumber 값 주입
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String randomNum="";

        Random random = new Random();
        for(int i=0;i<6;i++){
            randomNum+=random.nextInt(9 );
        }

        roomNumber=date+randomNum;

    }


    public Room(RoomRegistrationDto dto,User user,RoomInfo roomInfo) {
        this.address=new Address(dto.getPostcode(),dto.getAddress(),dto.getDetailAddress(),dto.getExtraAddress());
        this.user=user;
        this.roomInfo=roomInfo;
        this.registrant=dto.getRegistrant();
        this.lat=dto.getLat();
        this.lng=dto.getLng();
        this.deposit=dto.getDeposit();
        this.monthlyRent=dto.getMonthlyRent();
        this.houseType=dto.getHouseType();
        this.roomType=dto.getRoomType();
        this.maintenance=dto.getMaintenance();
        this.moveInDate=dto.getMoveInDate();
        this.maintenanceItem=dto.getMaintenanceItem();
        this.title=dto.getTitle();
        this.content=dto.getContent();

    }

    public static Room makeRoom(RoomRegistrationDto dto,RoomInfo roomInfo,String[] images,User user){
        Room room = new Room(dto, user, roomInfo);

        List<Photo> photoList = Arrays.stream(images).map(img -> new Photo(room, img)).collect(Collectors.toList());

        room.photos=photoList;

        return room;


    }


    public void delete(){

        photos.clear();
        this.entityStatus=EntityStatus.DELETE;


    }

    public void roomUpdate(RoomModifyDto dto,List<String> newImages){
        this.address=new Address(dto.getPostcode(),dto.getAddress(),dto.getDetailAddress(),dto.getExtraAddress());
        this.roomInfo.roomInfoUpdate(dto.getRoomInfoModifyDto());
        this.registrant=dto.getRegistrant();
        this.lat=dto.getLat();
        this.lng=dto.getLng();
        this.deposit=dto.getDeposit();
        this.monthlyRent=dto.getMonthlyRent();
        this.houseType=dto.getHouseType();
        this.roomType=dto.getRoomType();
        this.maintenance=dto.getMaintenance();
        this.moveInDate=dto.getMoveInDate();
        this.maintenanceItem=dto.getMaintenanceItem();
        this.title=dto.getTitle();
        this.content=dto.getContent();

        //이미지 삭제
        dto.getDelImg().forEach(delImg-> photos.removeIf(photo->photo.getImg().equals(delImg)));

        //새 이미지 저장
        newImages.forEach(newImage->photos.add(new Photo(this,newImage)));


    }

}
