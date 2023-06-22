package project.project.dto.roominfo;

import jakarta.validation.constraints.*;
import lombok.Data;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.Option;

import java.util.ArrayList;
import java.util.List;
@Data
public class RoomInfoRegistrationDto {
    @NotNull
    @Positive
    private Double realSize;  //전용면적
    @NotNull
    @Positive
    private Double supplySize; //공급면적
    @NotBlank
    private String floor; //해당 층
    @NotBlank
    private String entireFloor; //전체층
    @NotNull
    private Bearing bearing; //주실
    @NotNull
    private Boolean parking; //주차 여부
    @NotNull
    private Boolean animal; //반려동물 여부

    private List<Option> option=new ArrayList<>(); //옵션

    public RoomInfoRegistrationDto() {
    }

    public RoomInfoRegistrationDto(Double realSize, Double supplySize, String floor, String entireFloor, Bearing bearing, Boolean parking, Boolean animal, List<Option> option) {
        this.realSize = realSize;
        this.supplySize = supplySize;
        this.floor = floor;
        this.entireFloor = entireFloor;
        this.bearing = bearing;
        this.parking = parking;
        this.animal = animal;
        this.option = option;
    }
}
