package com.team2final.minglecrm.inquiry.domain.repository;

import com.team2final.minglecrm.inquiry.domain.InquiryAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryActionRepository extends JpaRepository<InquiryAction, Long> {
    InquiryAction findByInquiryId(Long inquiryId);
}