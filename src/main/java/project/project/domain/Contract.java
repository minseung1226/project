package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;
import project.project.domain.baseentity.BaseEntity;
import project.project.domain.embeded.Address;

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

    private LocalDate finalMoneyDate; // 잔금일
    private LocalDate moveInDate;  //입주일
    private LocalDate contractDate; //계약일
    private Integer monthlyRentDate; //월세 지급일
    private String monthlyRentType ;//월세 선불인지 후불인지의 여부
    private Integer  contractPeriod; //계약기간

    private List<String> specialAgreement;


}
