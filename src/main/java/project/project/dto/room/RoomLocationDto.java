package project.project.dto.room;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RoomLocationDto {

    private Long dto;
    private Double lat;
    private Double lng;


    @QueryProjection
    public RoomLocationDto(Long dto, Double lat, Double lng) {
        this.dto = dto;
        this.lat = lat;
        this.lng = lng;
    }
}
