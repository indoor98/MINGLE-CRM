package com.team2final.minglecrm.persistence.repository.log.queryDsl;

import com.team2final.minglecrm.controller.event.response.EmailLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailLogRepositoryCustom {
    Page<EmailLogResponse> findByEventId(Pageable pageable, Long eventId);

}
