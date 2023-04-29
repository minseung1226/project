package project.project.domain;

import jakarta.persistence.*;
import project.project.domain.embeded.Address;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "room_info_id")
    private RoomInfo roomInfo;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Photo> photos=new ArrayList<>();

    private int 보증금;
    private int 월세;
    private int 평수;
    private String 층수;
    private String 집구조;

}
