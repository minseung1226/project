package project.project.repository.contractrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.project.domain.QContract;
import project.project.domain.QUser;
import project.project.dto.contract.ContractDto;
import project.project.dto.contract.QContractDto;

import java.util.List;

import static project.project.domain.QContract.*;

@Repository
public class DslContractRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DslContractRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public ContractDto findContractDtoById(Long contractId){
        return queryFactory.select(
                        new QContractDto(
                                contract.id,
                                contract.user.id,
                                contract.roomAddress,
                                contract.designation,
                                contract.landRightsRatio,
                                contract.landSize,
                                contract.structureType,
                                contract.purpose,
                                contract.buildingSize,
                                contract.rentalArea,
                                contract.roomSize,
                                contract.deposit,
                                contract.earnestMoney,
                                contract.installmentPayment,
                                contract.finalMoney,
                                contract.monthlyRent,
                                contract.finalMoneyDate,
                                contract.midPaymentDate,
                                contract.moveInDate,
                                contract.contractDate,
                                contract.monthlyRentDate,
                                contract.monthlyRentType,
                                contract.contractPeriod,
                                contract.specialAgreement,
                                contract.lessorName,
                                contract.lessorPhone,
                                contract.lessorAddress,
                                contract.lessorResidentNumber,
                                contract.tenantName,
                                contract.tenantPhone,
                                contract.tenantAddress,
                                contract.tenantResidentNumber
                        ))
                .from(contract).join(contract.user, QUser.user)
                .where(contract.id.eq(contractId))
                .fetchOne();
    }


    public List<ContractDto> findContractDtosByUserId(Long userId){

        return queryFactory.select(
                new QContractDto(
                        contract.id,
                        contract.user.id,
                        contract.roomAddress,
                        contract.designation,
                        contract.landRightsRatio,
                        contract.landSize,
                        contract.structureType,
                        contract.purpose,
                        contract.buildingSize,
                        contract.rentalArea,
                        contract.roomSize,
                        contract.deposit,
                        contract.earnestMoney,
                        contract.installmentPayment,
                        contract.finalMoney,
                        contract.monthlyRent,
                        contract.finalMoneyDate,
                        contract.midPaymentDate,
                        contract.moveInDate,
                        contract.contractDate,
                        contract.monthlyRentDate,
                        contract.monthlyRentType,
                        contract.contractPeriod,
                        contract.specialAgreement,
                        contract.lessorName,
                        contract.lessorPhone,
                        contract.lessorAddress,
                        contract.lessorResidentNumber,
                        contract.tenantName,
                        contract.tenantPhone,
                        contract.tenantAddress,
                        contract.tenantResidentNumber
                ))
                .from(contract).join(contract.user, QUser.user)
                .where(contract.user.id.eq(userId))
                .fetch();
    }
}
