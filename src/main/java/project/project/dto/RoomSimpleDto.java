package project.project.dto;

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

    @QueryProjection
    public RoomSimpleDto(Long id, String roomNumber, String address, RoomStatus status, LocalDateTime createDate, Long wishlistCount, Long inquiryCount) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.address = address;
        this.status = status;
        this.createDate = createDate;
        this.wishlistCount = wishlistCount;
        this.inquiryCount = inquiryCount;
    }
}
