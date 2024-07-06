package com.team2final.minglecrm.event.domain.repository.queryDsl;

import com.team2final.minglecrm.event.dto.response.EventLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRespositoryCustom {

    Page<EventLogResponse> findAll(Pageable pageable);
}
