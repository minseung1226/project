package project.project.dto.roominfo;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.Option;

import java.util.ArrayList;
import java.util.List;
@Data
public class RoomInfoModifyDto {
    @Enumerated(EnumType.STRING)
    private Bearing bearing; // 주실 방향

    @Convert(converter = EnumListConverter.class)
    private List<Option> options; // 옵션
    private boolean animal; // 반려동물

    private boolean parking; // 주차 여부

    private String floor;  // 층수
    private String entireFloor; //전체층

    private double realSize;   //전용면적
    private double supplySize; //공급 면적

    @QueryProjection
    public RoomInfoModifyDto(Bearing bearing, List<Option> options, boolean animal, boolean parking, String floor, String entireFloor, double realSize, double supplySize) {
        this.bearing = bearing;
        this.options = options;
        this.animal = animal;
        this.parking = parking;
        this.floor = floor;
        this.entireFloor = entireFloor;
        this.realSize = realSize;
        this.supplySize = supplySize;
    }
}
