package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.project.domain.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

}
