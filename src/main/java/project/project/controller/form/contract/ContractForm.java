package project.project.controller.form.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.project.domain.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Data
public class ContractForm {


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

    private String depositKo; // 보증금 한국어
    private Integer earnestMoney; // 계약금

    private String earnestMoneyKo; // 계약금 한국어
    private Integer installmentPayment; // 중도금

    private String installmentPaymentKo; // 중도금 한국어
    private Integer finalMoney; // 잔금

    private String finalMoneyKo; //잔금 한국어
    private Integer monthlyRent; // 월세

    private String monthlyRentKo; //월세 한국어
    private LocalDate finalMoneyDate; // 잔금일
    private LocalDate midPaymentDate; // 중도금 지급일
    private LocalDate moveInDate;  //입주일
    private LocalDate contractDate; //계약일
    private Integer monthlyRentDate; //월세 지급일
    private String monthlyRentType ;//월세 선불인지 후불인지의 여부
    private List<String> specialAgreement; //특약사항
    private String lessorName; //임대인 이름
    private String lessorPhone; //임대인 전화번호
    private String lessorAddress; // 임대인 주소
    private String lessorResidentNumber; //임대인 주민번호
    private String tenantName; //임대인 이름
    private String tenantPhone; //임대인 전화번호
    private String tenantAddress; // 임대인 주소
    private String tenantResidentNumber; //임대인

    private LocalDate contractEndDate; //계약종료일



    public ContractForm(Contract contract) {

        this.roomAddress = contract.getRoomAddress();
        this.designation = contract.getDesignation();
        this.landRightsRatio = contract.getLandRightsRatio();
        this.landSize = contract.getLandSize();
        this.structureType = contract.getStructureType();
        this.purpose = contract.getPurpose();
        this.buildingSize = contract.getBuildingSize();
        this.rentalArea = contract.getRentalArea();
        this.roomSize = contract.getRoomSize();
        this.deposit = contract.getDeposit();
        this.earnestMoney = contract.getEarnestMoney();
        this.installmentPayment = contract.getInstallmentPayment();
        this.finalMoney = contract.getFinalMoney();
        this.monthlyRent = contract.getMonthlyRent();
        this.finalMoneyDate = contract.getFinalMoneyDate();
        this.midPaymentDate = contract.getMidPaymentDate();
        this.moveInDate = contract.getMoveInDate();
        this.contractDate = contract.getContractDate();
        this.monthlyRentDate = contract.getMonthlyRentDate();
        this.monthlyRentType = contract.getMonthlyRentType();
        this.specialAgreement = contract.getSpecialAgreement();
        this.lessorName = contract.getLessorName();
        this.lessorPhone = contract.getLessorPhone();
        this.lessorAddress = contract.getLessorAddress();
        this.lessorResidentNumber = contract.getLessorResidentNumber();
        this.tenantName = contract.getTenantName();
        this.tenantPhone = contract.getTenantPhone();
        this.tenantAddress = contract.getTenantAddress();
        this.tenantResidentNumber = contract.getTenantResidentNumber();

        //계약기간을 통해 계약종료일 구하기
        this.contractEndDate=contract.getMoveInDate().plusMonths(contract.getContractPeriod());

        this.deposit = contract.getDeposit();
        this.earnestMoney = contract.getEarnestMoney();
        this.installmentPayment = contract.getInstallmentPayment();
        this.finalMoney = contract.getFinalMoney();
        this.monthlyRent = contract.getMonthlyRent();
    }

    public void initializeKoreanFields(){
        this.depositKo=numberToKo(this.deposit);
        this.earnestMoneyKo=numberToKo(this.earnestMoney);
        if(installmentPayment!=null) this.installmentPaymentKo=numberToKo(installmentPayment);
        this.finalMoneyKo=numberToKo(this.finalMoney);
        this.monthlyRentKo=numberToKo(this.monthlyRent);
    }


    private String numberToKo(Integer money){
        Stack<String> stack=new Stack<>()   ;
        String[] digits={"","일","이","삼","사","오","육","칠","팔","구"};
        String[] units={"만","억"};
        String[] units2={"","십","백","천"};
        List<String> list=new ArrayList<>();

        money/=10000;
        while (money>0){
            Integer mod=money%10;
            list.add(digits[mod]);
            money/=10;
        }

        for(int i=0;i<list.size();i+=4){
            stack.push(units[i/4]);
            for(int j=i;j<i+4;j++){
                if(list.size()>j&&list.get(j)!=""){
                    if(j!=i&&list.get(j).equals("일")){
                        stack.add(units2[j-i]);
                    }
                    else{
                        stack.add(list.get(j)+units2[j-i]);
                    }
                }
            }
        }

        String result="";

        while (!stack.isEmpty()){
            String pop = stack.pop();
            result+=pop;
        }

        return result;
    }
}
