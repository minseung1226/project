package project.project.dto.room;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.Room;
import project.project.domain.converter.EnumListConverter;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.MaintenanceItem;
import project.project.domain.enum_type.RoomType;
import project.project.dto.photo.PhotoDto;
import project.project.dto.roominfo.RoomInfoModifyDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomModifyDto{

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    private String roomNumber;

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

    @Convert(converter = EnumListConverter.class)
    private List<MaintenanceItem> maintenanceItem =new ArrayList<>();

    private List<PhotoDto> img=new ArrayList<>();
    private List<MultipartFile> newImg =new ArrayList<>();
    private List<String> delImg=new ArrayList<>();

    @NotBlank
    private String title;

    private String content;

    private RoomInfoModifyDto roomInfoModifyDto;

    @QueryProjection
    public RoomModifyDto(Long id, Long userId,String roomNumber, String registrant, HouseType houseType, RoomType roomType, Integer postcode, String address, String detailAddress, String extraAddress, Double lat, Double lng, Integer deposit, Integer monthlyRent, Double maintenance
            , LocalDate moveInDate, List<MaintenanceItem> maintenanceItem, String title, String content,RoomInfoModifyDto roomInfoModifyDto) {
        this.id = id;
        this.userId = userId;
        this.roomNumber=roomNumber;
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
        this.title = title;
        this.content = content;
        this.roomInfoModifyDto=roomInfoModifyDto;
    }
}
