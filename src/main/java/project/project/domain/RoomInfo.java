package project.project.domain;

import jakarta.persistence.*;
import lombok.*;
import project.project.domain.baseentity.BaseEntity;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.enum_type.Bearing;

import project.project.domain.enum_type.Option;
import project.project.dto.roominfo.RoomInfoModifyDto;
import project.project.dto.roominfo.RoomInfoRegistrationDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "room_info_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private Bearing bearing; // 주실 방향

    @Convert(converter = EnumListConverter.class)
    @Enumerated(EnumType.STRING)
    @Column(length = 6000)
    private List<Option> options=new ArrayList<>(); // 옵션
    private boolean animal; // 반려동물
    private boolean parking; // 주차 여부

    private String floor;  // 층수
    private String entireFloor; //전체층

    private Double realSize;   //전용면적
    private Double supplySize; //공급 면적

    protected RoomInfo(RoomInfoRegistrationDto dto) {
        this.bearing=dto.getBearing();
        this.options=dto.getOption();
        this.animal=dto.getAnimal();
        this.parking=dto.getParking();
        this.floor=dto.getFloor();
        this.entireFloor=dto.getEntireFloor();
        this.realSize=dto.getRealSize();
        this.supplySize=dto.getSupplySize();
    }

    public static RoomInfo makeRoomInfo(RoomInfoRegistrationDto dto){
        RoomInfo roomInfo = new RoomInfo(dto);

        return roomInfo;
    }

    public void roomInfoUpdate(RoomInfoModifyDto dto) {
        this.bearing=dto.getBearing();
        this.options=dto.getOptions();
        this.animal=dto.isAnimal();
        this.parking=dto.isParking();
        this.floor=dto.getFloor();
        this.entireFloor=dto.getEntireFloor();
        this.realSize=dto.getRealSize();
        this.supplySize=dto.getSupplySize();

    }
}
