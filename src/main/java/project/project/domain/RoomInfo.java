package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.MaintenanceList;
import project.project.domain.enum_type.Option;
import project.project.domain.enum_type.RoomType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude ={"maintenanceList","options"})
public class RoomInfo {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomType roomType; //집 종류
    private double maintenance; // 관리비

    @Convert(converter = EnumListConverter.class)
    @Enumerated(EnumType.STRING)
    private List<MaintenanceList> maintenanceList=new ArrayList<>(); //관리비포함항목

    @Enumerated(EnumType.STRING)
    private Bearing bearing; // 주실 방향

    @Convert(converter = EnumListConverter.class)
    @Enumerated(EnumType.STRING)
    private List<Option> options=new ArrayList<>(); // 옵션
    private boolean animal; // 반려동물
    private boolean parking; // 주차 여부
    private boolean ev;      // 엘리베이터 유무
    private LocalDateTime moveInDate; // 입주일

    public RoomInfo(RoomType roomType, double maintenance, List<MaintenanceList> maintenanceList
            , Bearing bearing, List<Option> options, boolean animal, boolean parking, boolean ev
            , LocalDateTime moveInDate) {
        this.roomType = roomType;
        this.maintenance = maintenance;
        this.maintenanceList = maintenanceList;
        this.bearing = bearing;
        this.options = options;
        this.animal = animal;
        this.parking = parking;
        this.ev = ev;
        this.moveInDate = moveInDate;
    }

    public static RoomInfo makeRoomInfo(RoomType roomType,double maintenance, List<MaintenanceList> maintenanceList,
                                    Bearing bearing, List<Option> options, boolean animal, boolean parking,
                                    boolean ev, LocalDateTime moveInDate){
        return new RoomInfo(roomType,maintenance,maintenanceList,bearing,options
                ,animal,parking,ev,moveInDate);
    }
}
