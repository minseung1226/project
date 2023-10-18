package project.project.dto.room;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RoomLocationDto {

    private Long id;
    private Double lat;
    private Double lng;


    @QueryProjection
    public RoomLocationDto(Long id, Double lat, Double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }
}
