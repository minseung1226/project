package project.project.dto.photo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PhotoDto {

    private Long id;
    private String img;


    @QueryProjection
    public PhotoDto(Long id, String img) {
        this.id = id;
        this.img = img;
    }
}
