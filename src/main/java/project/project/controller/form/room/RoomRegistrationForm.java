package project.project.controller.form.room;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.enum_type.HouseType;
import project.project.domain.enum_type.MaintenanceItem;
import project.project.domain.enum_type.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class RoomRegistrationForm {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private String registrant; //등록자
    @NotEmpty
    private HouseType houseType; //건물 종류
    @NotEmpty
    private RoomType roomType; //방 종류
    @NotEmpty
    private String postcode;
    @NotEmpty
    private String address;
    @NotEmpty
    private String detailAddress;
    private String extraAddress;
    @NotEmpty
    private  Integer deposit; //보증금
    @NotEmpty
    private Integer monthlyRent; //월세
    @NotEmpty
    private double maintenance; //관리비
    @NotEmpty
    private LocalDate moveInDate; //입주일
    private List<MaintenanceItem> maintenanceItem =new ArrayList<>();

    private List<MultipartFile> img =new ArrayList<>();
}
