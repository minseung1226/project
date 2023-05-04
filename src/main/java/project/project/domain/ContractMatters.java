package project.project.domain;

import jakarta.persistence.*;

@Entity
public class ContractMatters {

    @Id
    @GeneratedValue
    @Column(name = "contract_matters_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;


}
