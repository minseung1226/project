package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.UEncoder;
import org.w3c.dom.stylesheets.LinkStyle;
import project.project.domain.baseentity.BaseEntity;
import project.project.domain.converter.StringListConverter;
import project.project.domain.embeded.Address;
import project.project.dto.contract.ContractDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String roomAddress;
    private String designation; // 지목
    private String landRightsRatio; // 대지권 비율
    private Double landSize; // 토지면적

    private String structureType ;// 건물 구조
    private String purpose; // 용도
    private Double buildingSize; // 건물 면적

    private String rentalArea; // 임대할 부분
    private Double roomSize; // 임대 면적

    private Integer deposit; // 보증금
    private Integer earnestMoney; // 계약금
    private Integer installmentPayment; // 중도금
    private Integer finalMoney; // 잔금
    private Integer monthlyRent; // 월세

    private LocalDate midPaymentDate; // 중도금 지급일
    private LocalDate finalMoneyDate; // 잔금일
    private LocalDate moveInDate;  //입주일
    private LocalDate contractDate; //계약일
    private Integer monthlyRentDate; //월세 지급일
    private String monthlyRentType ;//월세 선불인지 후불인지의 여부
    private Integer  contractPeriod; //계약기간

    @Convert(converter = StringListConverter.class)
    private List<String> specialAgreement; //특약사항

    private String lessorName; //임대인 이름
    private String lessorPhone; //임대인 전화번호
    private String lessorAddress; // 임대인 주소
    private String lessorResidentNumber; //임대인 주민번호

    private String tenantName; //임대인 이름
    private String tenantPhone; //임대인 전화번호
    private String tenantAddress; // 임대인 주소
    private String tenantResidentNumber; //임대인 주민번호


    public Contract(ContractDto contractDto, User user) {
        this.user = user;
        this.roomAddress = contractDto.getRoomAddress();
        this.designation = contractDto.getDesignation();
        this.landRightsRatio = contractDto.getLandRightsRatio();
        this.landSize = contractDto.getLandSize();
        this.structureType = contractDto.getStructureType();
        this.purpose = contractDto.getPurpose();
        this.buildingSize = contractDto.getBuildingSize();
        this.rentalArea = contractDto.getRentalArea();
        this.roomSize = contractDto.getRoomSize();
        this.deposit = contractDto.getDeposit();
        this.earnestMoney = contractDto.getEarnestMoney();
        this.installmentPayment = contractDto.getInstallmentPayment();
        this.finalMoney = contractDto.getFinalMoney();
        this.monthlyRent = contractDto.getMonthlyRent();
        this.midPaymentDate = contractDto.getMidPaymentDate();
        this.finalMoneyDate = contractDto.getFinalMoneyDate();
        this.moveInDate = contractDto.getMoveInDate();
        this.contractDate = contractDto.getContractDate();
        this.monthlyRentDate = contractDto.getMonthlyRentDate();
        this.monthlyRentType = contractDto.getMonthlyRentType();
        this.contractPeriod = contractDto.getContractPeriod();
        this.specialAgreement = contractDto.getSpecialAgreement();
        this.lessorName = contractDto.getLessorName();
        this.lessorPhone = contractDto.getLessorPhone();
        this.lessorAddress = contractDto.getLessorAddress();
        this.lessorResidentNumber = contractDto.getLessorResidentNumber();
        this.tenantName = contractDto.getTenantName();
        this.tenantPhone = contractDto.getTenantPhone();
        this.tenantAddress = contractDto.getTenantAddress();
        this.tenantResidentNumber = contractDto.getTenantResidentNumber();
    }

    public void update(ContractDto contractDto){
        this.roomAddress = contractDto.getRoomAddress();
        this.designation = contractDto.getDesignation();
        this.landRightsRatio = contractDto.getLandRightsRatio();
        this.landSize = contractDto.getLandSize();
        this.structureType = contractDto.getStructureType();
        this.purpose = contractDto.getPurpose();
        this.buildingSize = contractDto.getBuildingSize();
        this.rentalArea = contractDto.getRentalArea();
        this.roomSize = contractDto.getRoomSize();
        this.deposit = contractDto.getDeposit();
        this.earnestMoney = contractDto.getEarnestMoney();
        this.installmentPayment = contractDto.getInstallmentPayment();
        this.finalMoney = contractDto.getFinalMoney();
        this.monthlyRent = contractDto.getMonthlyRent();
        this.midPaymentDate = contractDto.getMidPaymentDate();
        this.finalMoneyDate = contractDto.getFinalMoneyDate();
        this.moveInDate = contractDto.getMoveInDate();
        this.contractDate = contractDto.getContractDate();
        this.monthlyRentDate = contractDto.getMonthlyRentDate();
        this.monthlyRentType = contractDto.getMonthlyRentType();
        this.contractPeriod = contractDto.getContractPeriod();
        this.specialAgreement = contractDto.getSpecialAgreement();
        this.lessorName = contractDto.getLessorName();
        this.lessorPhone = contractDto.getLessorPhone();
        this.lessorAddress = contractDto.getLessorAddress();
        this.lessorResidentNumber = contractDto.getLessorResidentNumber();
        this.tenantName = contractDto.getTenantName();
        this.tenantPhone = contractDto.getTenantPhone();
        this.tenantAddress = contractDto.getTenantAddress();
        this.tenantResidentNumber = contractDto.getTenantResidentNumber();
    }
}
