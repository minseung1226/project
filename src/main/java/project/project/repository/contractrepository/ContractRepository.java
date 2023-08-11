package project.project.repository.contractrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Contract;

public interface ContractRepository extends JpaRepository<Contract,Long> {



}