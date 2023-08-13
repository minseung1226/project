package project.project.dto.contract;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.project.domain.converter.StringListConverter;

import java.time.LocalDate;
import java.util.List;

@Data
public class ContractDto {

    private Long id;
    private Long userId;

    @NotBlank
    private String roomAddress;
    @NotBlank
    private String designation; // 지목

    private String landRightsRatio; // 대지권 비율
    @NotNull
    private Double landSize; // 토지면적
    @NotBlank
    private String structureType ;// 건물 구조
    @NotBlank
    private String purpose; // 용도
    @NotNull
    private Double buildingSize; // 건물 면적
    @NotBlank
    private String rentalArea; // 임대할 부분
    @NotNull
    private Double roomSize; // 임대 면적
    @NotNull
    private Integer deposit; // 보증금
    @NotNull
    private Integer earnestMoney; // 계약금
    private Integer installmentPayment; // 중도금
    @NotNull
    private Integer finalMoney; // 잔금
    @NotNull
    private Integer monthlyRent; // 월세
    @NotNull
    private LocalDate finalMoneyDate; // 잔금일

    private LocalDate midPaymentDate; // 중도금 지급일
    @NotNull
    private LocalDate moveInDate;  //입주일
    @NotNull
    private LocalDate contractDate; //계약일
    @NotNull
    private Integer monthlyRentDate; //월세 지급일
    @NotBlank
    private String monthlyRentType ;//월세 선불인지 후불인지의 여부
    @NotNull
    private Integer  contractPeriod; //계약기간

    private List<String> specialAgreement; //특약사항
    @NotBlank
    private String lessorName; //임대인 이름
    @NotBlank
    private String lessorPhone; //임대인 전화번호
    @NotBlank
    private String lessorAddress; // 임대인 주소
    @NotBlank
    private String lessorResidentNumber; //임대인 주민번호
    @NotBlank
    private String tenantName; //임대인 이름
    @NotBlank
    private String tenantPhone; //임대인 전화번호
    @NotBlank
    private String tenantAddress; // 임대인 주소
    @NotBlank
    private String tenantResidentNumber; //임대인

    public ContractDto() {
    }

    @QueryProjection
    public ContractDto(Long id, Long userId, String roomAddress, String designation,
                       String landRightsRatio, Double landSize, String structureType,
                       String purpose, Double buildingSize, String rentalArea, Double roomSize,
                       Integer deposit, Integer earnestMoney, Integer installmentPayment,
                       Integer finalMoney, Integer monthlyRent, LocalDate finalMoneyDate,
                       LocalDate midPaymentDate, LocalDate moveInDate, LocalDate contractDate,
                       Integer monthlyRentDate, String monthlyRentType, Integer contractPeriod,
                       List<String> specialAgreement, String lessorName, String lessorPhone,
                       String lessorAddress, String lessorResidentNumber, String tenantName,
                       String tenantPhone, String tenantAddress, String tenantResidentNumber) {
        this.id = id;
        this.userId = userId;
        this.roomAddress = roomAddress;
        this.designation = designation;
        this.landRightsRatio = landRightsRatio;
        this.landSize = landSize;
        this.structureType = structureType;
        this.purpose = purpose;
        this.buildingSize = buildingSize;
        this.rentalArea = rentalArea;
        this.roomSize = roomSize;
        this.deposit = deposit;
        this.earnestMoney = earnestMoney;
        this.installmentPayment = installmentPayment;
        this.finalMoney = finalMoney;
        this.monthlyRent = monthlyRent;
        this.finalMoneyDate = finalMoneyDate;
        this.midPaymentDate = midPaymentDate;
        this.moveInDate = moveInDate;
        this.contractDate = contractDate;
        this.monthlyRentDate = monthlyRentDate;
        this.monthlyRentType = monthlyRentType;
        this.contractPeriod = contractPeriod;
        this.specialAgreement = specialAgreement;
        this.lessorName = lessorName;
        this.lessorPhone = lessorPhone;
        this.lessorAddress = lessorAddress;
        this.lessorResidentNumber = lessorResidentNumber;
        this.tenantName = tenantName;
        this.tenantPhone = tenantPhone;
        this.tenantAddress = tenantAddress;
        this.tenantResidentNumber = tenantResidentNumber;
    }
}
