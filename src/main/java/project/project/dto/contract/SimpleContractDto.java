package project.project.dto.contract;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SimpleContractDto {

    private Long id;
    private String roomAddress;
    private String lessorName;
    private String tenantName;
    private Integer deposit;
    private Integer monthlyRent;
    private LocalDate contractDate;
    private Integer contractPeriod;

    @QueryProjection
    public SimpleContractDto(Long id, String roomAddress, String lessorName, String tenantName, Integer deposit, Integer monthlyRent, LocalDate contractDate, Integer contractPeriod) {
        this.id = id;
        this.roomAddress = roomAddress;
        this.lessorName = lessorName;
        this.tenantName = tenantName;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.contractDate = contractDate;
        this.contractPeriod = contractPeriod;
    }
}
