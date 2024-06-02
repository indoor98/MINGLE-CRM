package com.team2final.minglecrm.persistence.repository.inquiry;

import com.team2final.minglecrm.entity.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    @Query("SELECT i FROM Inquiry i WHERE i NOT IN (SELECT ir.inquiry FROM InquiryReply ir)")
    List<Inquiry> findUnansweredInquiries();

    @Query("SELECT i FROM Inquiry i WHERE i.id IN (SELECT ir.inquiry.id FROM InquiryReply ir)")
    List<Inquiry> findInquiriesWithReply();

    @Query("SELECT i FROM Inquiry i WHERE i.id IN (SELECT ia.inquiry.id FROM InquiryAction ia)")
    List<Inquiry> findInquiriesWithAction();

    @Query("SELECT i FROM Inquiry i WHERE i.id NOT IN (SELECT ia.inquiry.id FROM InquiryAction ia)")
    List<Inquiry> findInquiriesWithoutAction();

    @Query("SELECT i FROM Inquiry i WHERE i.customer.id = :customerId")
    List<Inquiry> findByCustomerId(Long customerId);

    Optional<Inquiry> findByIdAndCustomerId(Long inquiryId, Long customerId);

    @Query("SELECT i FROM Inquiry i " +
            "JOIN i.customer c " +
            "WHERE (i.inquiryTitle LIKE %:keyword% OR i.inquiryContent LIKE %:keyword% OR c.name LIKE %:keyword%) " +
            "AND i.date BETWEEN :startDate AND :endDate")
    List<Inquiry> searchByKeyword(@Param("keyword") String keyword,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

}
