package project.project.controller.form.room;

import lombok.Data;
import project.project.domain.enum_type.RoomType;
@Data
public class RoomWishlistForm {

    private Long wishlistId;
    private Long roomId;
    private String img;
    private Double realSize;
    private String title;
    private RoomType roomType;
    private Integer deposit;
    private Integer monthlyRent;
    private Double maintenance;

    public RoomWishlistForm(Long wishlistId, Long roomId, String img, Double realSize, String title, RoomType roomType, Integer deposit, Integer monthlyRent, Double maintenance) {
        this.wishlistId = wishlistId;
        this.roomId = roomId;
        this.img = img;
        this.realSize = realSize;
        this.title = title;
        this.roomType = roomType;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.maintenance = maintenance;
    }
}
