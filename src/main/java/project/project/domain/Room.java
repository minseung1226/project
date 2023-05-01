package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.HouseType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

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

    private String img; // 대표 이미지
    private int deposit; //보증금
    private int monthlyRent; //월세
    private double area;   //전용면적
    private String floor;  // 층수
    @Enumerated(EnumType.STRING)
    private HouseType houseType; //집종류

    public Room(Address address, User user, RoomInfo roomInfo, List<Photo> photos
            , String img, int deposit, int monthlyRent, double area, String floor
            , HouseType houseType) {
        this.address = address;
        this.user = user;
        this.roomInfo = roomInfo;
        this.photos = photos;
        this.img = img;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.area = area;
        this.floor = floor;
        this.houseType = houseType;
    }

    private static Room makeRoom(Address address, User user, RoomInfo roomInfo, List<Photo> photos,
                                 String img, int deposit, int monthlyRent, double area, String floor,
                                 HouseType houseType){
     return new Room(address,user,roomInfo,photos,img,deposit,monthlyRent,area,floor,houseType);
    }

}
