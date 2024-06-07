package com.team2final.minglecrm.persistence.repository.inquiry;

import com.team2final.minglecrm.entity.inquiry.InquiryAction;
import com.team2final.minglecrm.entity.inquiry.InquiryReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryActionRepository extends JpaRepository<InquiryAction, Long> {
    InquiryAction findByInquiryId(Long inquiryId);
}