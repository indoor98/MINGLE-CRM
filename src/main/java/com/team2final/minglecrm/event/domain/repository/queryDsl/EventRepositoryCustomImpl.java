package com.team2final.minglecrm.event.domain.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.employee.domain.QEmployee;
import com.team2final.minglecrm.event.domain.QEvent;
import com.team2final.minglecrm.event.dto.response.EventLogResponse;
import com.team2final.minglecrm.event.dto.response.QEventLogResponse;
import com.team2final.minglecrm.log.domain.QEmailLog;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EventRepositoryCustomImpl implements EventRespositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public EventRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional
    public Page<EventLogResponse> findAll(Pageable pageable) {
        QEvent event = QEvent.event;
        QEmployee employee = QEmployee.employee;
        QEmailLog emailLog = QEmailLog.emailLog;

        List<EventLogResponse> response = queryFactory
                .select(new QEventLogResponse(
                        event.id,
                        employee.id,
                        employee.name,
                        event.title,
                        event.content,
                        event.sentDate,
                        event.sendCount,
                        emailLog.isOpened.eq(true).count()
                ))
                .from(event)
                .innerJoin(event.employee, employee)
                .innerJoin(emailLog).on(emailLog.event.eq(event))
                .groupBy(event.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(response, pageable, response.size());
    }


}
