package com.team2final.minglecrm.persistence.repository.inquiry;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.entity.customer.QCustomer;
import com.team2final.minglecrm.entity.inquiry.Inquiry;
import com.team2final.minglecrm.entity.inquiry.QInquiry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    public InquiryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Inquiry> searchByKeyword(String keyword, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QInquiry inquiry = QInquiry.inquiry;
        QCustomer customer = QCustomer.customer;

        BooleanExpression predicate = inquiry.inquiryTitle.containsIgnoreCase(keyword)
                .or(inquiry.inquiryContent.containsIgnoreCase(keyword))
                .or(customer.name.containsIgnoreCase(keyword))
                .or(customer.phone.containsIgnoreCase(keyword));

        return new PageImpl<>(
                queryFactory.selectFrom(inquiry)
                        .join(inquiry.customer, customer)
                        .where(predicate)
                        .where(inquiry.date.between(startDate, endDate))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch(),
                pageable,
                queryFactory.selectFrom(inquiry)
                        .join(inquiry.customer, customer)
                        .where(predicate)
                        .where(inquiry.date.between(startDate, endDate))
                        .fetchCount()
        );
    }
}