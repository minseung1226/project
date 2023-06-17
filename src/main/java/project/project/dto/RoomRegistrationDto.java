package project.project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.MaintenanceItem;
import project.project.domain.enum_type.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class RoomRegistrationDto {
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

    @Size(min = 3,max = 10)
    private List<MultipartFile> img =new ArrayList<>();
}
