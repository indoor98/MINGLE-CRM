package com.team2final.minglecrm.persistence.repository.event.queryDsl;

import com.team2final.minglecrm.controller.event.response.EventLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRespositoryCustom {

    Page<EventLogResponse> findAll(Pageable pageable);
}
