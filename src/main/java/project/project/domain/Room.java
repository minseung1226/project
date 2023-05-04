package project.project.domain;

import jakarta.persistence.*;
import lombok.*;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.Level;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"user","roomInfo","photos","address"})
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

    @Enumerated(EnumType.STRING)
    private Level level;

    public Room(Address address, User user, RoomInfo roomInfo, List<Photo> photos
            , String img, int deposit, int monthlyRent, double area, String floor
            , HouseType houseType,Level level) {
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
        this.level=level;
    }

    public static Room makeRoom(Address address, User user, RoomInfo roomInfo, List<Photo> photos,
                                 String img, int deposit, int monthlyRent, double area, String floor,
                                 HouseType houseType,Level level){
        Room room = new Room(address, user, roomInfo, photos, img, deposit, monthlyRent, area, floor, houseType,level);

        for(int i=0;i<room.photos.size();i++){
            room.photos.get(i).changeRoom(room);
        }

        return room;
    }

}
