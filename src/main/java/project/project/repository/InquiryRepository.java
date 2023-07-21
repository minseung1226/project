package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.Inquiry;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

    @Query("select i from Inquiry i join fetch i.room join fetch i.user where i.user.id=:userId")
    public List<Inquiry> findInquirysByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("delete from Inquiry i where i.id in :inquiryIds")
    public void bulkDeleteByIds(@Param("inquiryIds") List<Long> inquiryIds);
}
