package project.project.domain;

import jakarta.persistence.*;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.HouseType;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    private int deposit; //보증금
    private int monthlyRent; //월세
    private int area;   //전용면적
    private String floor;  // 층수
    @Enumerated(EnumType.STRING)
    private HouseType houseType; //집종류

}
