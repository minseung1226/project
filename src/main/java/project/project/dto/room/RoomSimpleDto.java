package project.project.dto.room;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import project.project.domain.enum_type.RoomStatus;

import java.time.LocalDateTime;

@Data
public class RoomSimpleDto {
    private Long id;
    private String roomNumber;
    private String address;
    private RoomStatus status;

    private LocalDateTime createDate;
    private Long wishlistCount;
    private Long inquiryCount;

    private int deposit;
    private int monthlyRent;

    @QueryProjection
    public RoomSimpleDto(Long id, String roomNumber, String address, RoomStatus status, LocalDateTime createDate, Long wishlistCount, Long inquiryCount, int deposit, int monthlyRent) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.address = address;
        this.status = status;
        this.createDate = createDate;
        this.wishlistCount = wishlistCount;
        this.inquiryCount = inquiryCount;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
    }
}
