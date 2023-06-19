package project.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import project.project.domain.enum_type.RoomStatus;

@Data
public class RoomsDto {
    private Long id;
    private String roomNumber;
    private String address;
    private RoomStatus status;
    private int wishlistCount;
    private int inquiryCount;

    @QueryProjection
    public RoomsDto(Long id, String roomNumber, String address, RoomStatus status, int wishlistCount, int inquiryCount) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.address = address;
        this.status = status;
        this.wishlistCount = wishlistCount;
        this.inquiryCount = inquiryCount;
    }
}
