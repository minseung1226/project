package project.project.controller.form.room;

import jakarta.validation.constraints.*;
import lombok.Data;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.Option;

import java.util.ArrayList;
import java.util.List;
@Data
public class RoomInfoRegistrationForm {
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

}
