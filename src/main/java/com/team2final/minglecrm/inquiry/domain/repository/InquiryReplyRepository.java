package com.team2final.minglecrm.inquiry.domain.repository;

import com.team2final.minglecrm.inquiry.domain.InquiryReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryReplyRepository extends JpaRepository<InquiryReply, Long> {
    InquiryReply findByInquiryId(Long inquiryId);
}
