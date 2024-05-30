package com.team2final.minglecrm.persistence.repository.inquiry;

import com.team2final.minglecrm.entity.inquiry.Inquiry;
import com.team2final.minglecrm.entity.inquiry.InquiryReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryReplyRepository extends JpaRepository<InquiryReply, Long> {
    Optional<InquiryReply> findByInquiryId(Long inquiryId);
}
