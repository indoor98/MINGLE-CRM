package com.team2final.minglecrm.inquiry.domain.repository;

import com.team2final.minglecrm.inquiry.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    @Query("SELECT i FROM Inquiry i WHERE i NOT IN (SELECT ir.inquiry FROM InquiryReply ir)")
    Page<Inquiry> findUnansweredInquiries(Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.id IN (SELECT ir.inquiry.id FROM InquiryReply ir)")
    Page<Inquiry> findInquiriesWithReply(Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.id IN (SELECT ia.inquiry.id FROM InquiryAction ia)")
    Page<Inquiry> findInquiriesWithAction(Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.id NOT IN (SELECT ia.inquiry.id FROM InquiryAction ia)")
    Page<Inquiry> findInquiriesWithoutAction(Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.customer.id = :customerId")
    Page<Inquiry> findByCustomerId(Long customerId, Pageable pageable);

    Optional<Inquiry> findByIdAndCustomerId(Long inquiryId, Long customerId);

    @Query("SELECT i FROM Inquiry i WHERE i.customer.name LIKE %:customerName%")
    Page<Inquiry> findByCustomerName(@Param("customerName") String customerName, Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.customer.phone LIKE %:customerPhone%")
    Page<Inquiry> findByCustomerPhone(@Param("customerPhone") String customerPhone, Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.inquiryTitle LIKE %:inquiryTitle%")
    Page<Inquiry> findByInquiryTitle(@Param("inquiryTitle") String inquiryTitle, Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.inquiryContent LIKE %:inquiryContent%")
    Page<Inquiry> findByInquiryContent(@Param("inquiryContent") String inquiryContent, Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.date BETWEEN :startDate AND :endDate")
    Page<Inquiry> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("SELECT i FROM Inquiry i " +
            "JOIN i.customer c " +
            "WHERE (i.inquiryTitle LIKE %:keyword% OR i.inquiryContent LIKE %:keyword% OR c.name LIKE %:keyword%) " +
            "AND i.date BETWEEN :startDate AND :endDate")
    Page<Inquiry> searchByKeyword(@Param("keyword") String keyword,
                                  @Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  Pageable pageable);

}
