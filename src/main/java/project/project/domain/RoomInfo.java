package project.project.domain;

import jakarta.persistence.*;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.MaintenanceList;
import project.project.domain.enum_type.Option;
import project.project.domain.enum_type.RoomType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RoomInfo {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomType roomType; //집 종류
    private int maintenance; // 관리비

    @ElementCollection(targetClass=MaintenanceList.class)
    @Enumerated(EnumType.STRING)
    private List<MaintenanceList> maintenanceList=new ArrayList<>(); //관리비포함항목

    @Enumerated(EnumType.STRING)
    private Bearing bearing; // 주실 방향

    @ElementCollection(targetClass = Option.class)
    @Enumerated(EnumType.STRING)
    private List<Option> option; // 옵션
    private boolean animal; // 반려동물
    private boolean parking; // 주차 여부
    private boolean ev;      // 엘리베이터 유무
    private LocalDateTime moveInDate; // 입주일
}
