package project.project.domain;

import jakarta.persistence.*;
import project.project.domain.enum_type.MaintenanceList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RoomInfo {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;

    private String 방구조;
    private int maintenance; // 관리비

    @ElementCollection(targetClass=MaintenanceList.class)
    @Enumerated(EnumType.STRING)
    private List<MaintenanceList> maintenanceList=new ArrayList<>(); //관리비포함항목

    private String 주실방향;
    private String 옵션;
    private String 반려동물여부;
    private String 주차여부;
    private String 엘리베이터여부;
    private LocalDateTime 입주가능일;
}
