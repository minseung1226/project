package project.project.dto.room;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.MaintenanceItem;
import project.project.domain.enum_type.RoomType;
import project.project.dto.photo.PhotoDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomModifyDto{

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String registrant; //등록자
    @NotNull
    private HouseType houseType; //건물 종류
    @NotNull
    private RoomType roomType; //방 종류
    @NotNull
    @Positive
    private Integer postcode;
    @NotBlank
    private String address;
    @NotBlank
    private String detailAddress;
    private String extraAddress;

    @Positive
    @NotNull
    private Double lat;

    @Positive
    @NotNull
    private Double lng;

    @NotNull
    @Positive
    private  Integer deposit; //보증금
    @NotNull
    @Positive
    private Integer monthlyRent; //월세
    @NotNull
    @Positive
    private Double maintenance; //관리비
    @NotNull
    private LocalDate moveInDate; //입주일
    private List<MaintenanceItem> maintenanceItem =new ArrayList<>();

    private List<PhotoDto> img=new ArrayList<>();
    private List<MultipartFile> newImg =new ArrayList<>();
    private List<String> delImg=new ArrayList<>();

    @NotBlank
    private String title;

    private String content;


}
