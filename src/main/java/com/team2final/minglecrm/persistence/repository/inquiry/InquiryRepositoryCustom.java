package com.team2final.minglecrm.persistence.repository.inquiry;

import com.team2final.minglecrm.entity.inquiry.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface InquiryRepositoryCustom {
    Page<Inquiry> searchByKeyword(String keyword, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}