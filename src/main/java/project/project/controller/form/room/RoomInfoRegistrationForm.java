package project.project.controller.form.room;

import lombok.Data;
import project.project.domain.enum_type.Bearing;
import project.project.domain.enum_type.Option;

import java.util.ArrayList;
import java.util.List;
@Data
public class RoomInfoRegistrationForm {
    private Double realSize;  //전용면적
    private Double supplySize; //공급면적
    private String floor; //해당 층
    private String entireFloor; //전체층
    private Bearing bearing; //주실
    private Boolean parking; //주차 여부
    private Boolean animal; //반려동물 여부
    private List<Option> option=new ArrayList<>(); //옵션

}
