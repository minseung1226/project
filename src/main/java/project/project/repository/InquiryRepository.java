package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
}
