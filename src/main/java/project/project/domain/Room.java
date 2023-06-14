package project.project.domain;

import jakarta.persistence.*;
import lombok.*;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.MaintenanceItem;
import project.project.domain.enum_type.RoomType;
import java.time.LocalDate;
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

    private String registrant; // 등록자 정보 : 세입자,임대인
    private double lat; //위도
    private double lng;  //경도

    private String img; // 대표 이미지

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






}
