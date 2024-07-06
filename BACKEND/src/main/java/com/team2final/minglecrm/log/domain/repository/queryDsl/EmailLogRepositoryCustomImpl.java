package com.team2final.minglecrm.log.domain.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.employee.domain.QEmployee;
import com.team2final.minglecrm.event.domain.QEvent;
import com.team2final.minglecrm.event.dto.response.EmailLogResponse;
import com.team2final.minglecrm.event.dto.response.QEmailLogResponse;
import com.team2final.minglecrm.log.domain.QEmailLog;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailLogRepositoryCustomImpl implements EmailLogRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public EmailLogRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<EmailLogResponse> findByEventId(Pageable pageable, Long eventId) {

        QEmailLog emailLog = QEmailLog.emailLog;
        QEvent event = QEvent.event;
        QEmployee employee = QEmployee.employee;
        QCustomer customer = QCustomer.customer;

        List<EmailLogResponse> response = queryFactory
                .select(new QEmailLogResponse(
                        event.title,
                        event.content,
                        employee.name,
                        employee.id,
                        event.sentDate,
                        event.sendCount,
                        emailLog.id.count(),
                        emailLog.isOpened,
                        emailLog.openedTime,
                        customer.id,
                        customer.name
                ))
                .from(emailLog)
                .innerJoin(event).on(emailLog.event.eq(event))
                .innerJoin(emailLog.customer, customer)
                .innerJoin(event.employee, employee)
                .where(event.id.eq(eventId))
                .groupBy(emailLog.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(response, pageable, response.size());
    }
}
