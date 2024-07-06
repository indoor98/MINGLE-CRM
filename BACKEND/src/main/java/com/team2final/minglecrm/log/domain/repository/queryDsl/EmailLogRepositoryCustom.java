package com.team2final.minglecrm.log.domain.repository.queryDsl;

import com.team2final.minglecrm.event.dto.response.EmailLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailLogRepositoryCustom {
    Page<EmailLogResponse> findByEventId(Pageable pageable, Long eventId);

}
