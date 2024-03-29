package project.project.dto.room;

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

    @NotBlank
    private String title;

    private String content;

    public RoomRegistrationDto() {
    }

    public RoomRegistrationDto(Long userId, String registrant, HouseType houseType, RoomType roomType,
                               Integer postcode, String address, String detailAddress, String extraAddress,
                               Double lat, Double lng, Integer deposit, Integer monthlyRent,
                               Double maintenance, LocalDate moveInDate, List<MaintenanceItem> maintenanceItem,
                               List<MultipartFile> img,String title,String content) {
        this.userId = userId;
        this.registrant = registrant;
        this.houseType = houseType;
        this.roomType = roomType;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
        this.lat = lat;
        this.lng = lng;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.maintenance = maintenance;
        this.moveInDate = moveInDate;
        this.maintenanceItem = maintenanceItem;
        this.img = img;
        this.title=title;
        this.content=content;
    }
}
