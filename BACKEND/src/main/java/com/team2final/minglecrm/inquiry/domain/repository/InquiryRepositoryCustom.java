package com.team2final.minglecrm.inquiry.domain.repository;

import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import com.team2final.minglecrm.inquiry.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface InquiryRepositoryCustom {
    Page<Inquiry> searchByCondition(String keyword, String customerName, String customerPhone, String inquiryTitle, String inquiryContent, LocalDateTime startDate, LocalDateTime endDate, String type, Boolean isReply, ActionStatus actionStatus, Pageable pageable);
}