package project.project.controller.form.room;

import lombok.Data;
import project.project.domain.enum_type.RoomType;

@Data
public class RoomMapForm {
    private Long id;
    private String img;
    private Double realSize;
    private Double lat;
    private Double lng;
    private String title;
    private RoomType roomType;

    private Integer deposit;
    private Integer monthlyRent;
    private Double maintenance;



    public RoomMapForm(Long id, String img, Double realSize, Double lat, Double lng, String title, RoomType roomType, Integer deposit, Integer monthlyRent, Double maintenance) {
        this.id = id;
        this.img = img;
        this.realSize = realSize;
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.roomType = roomType;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.maintenance = maintenance;
    }
}
