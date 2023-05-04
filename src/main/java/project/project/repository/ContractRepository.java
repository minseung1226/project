package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Contract;

public interface ContractRepository extends JpaRepository<Contract,Long> {

}
