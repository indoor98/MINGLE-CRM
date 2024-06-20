package com.team2final.minglecrm.inquiry.domain.repository;

import com.team2final.minglecrm.inquiry.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface InquiryRepositoryCustom {
    Page<Inquiry> searchByKeyword(String keyword, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}