package com.team2final.minglecrm.persistence.repository.inquiry;

import com.team2final.minglecrm.entity.inquiry.InquiryAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryActionRepository extends JpaRepository<InquiryAction, Long> {
    InquiryAction findByInquiryId(Long inquiryId);
}