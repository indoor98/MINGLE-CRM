package com.team2final.minglecrm.inquiry.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.inquiry.domain.ActionStatus;
import com.team2final.minglecrm.inquiry.domain.Inquiry;
import com.team2final.minglecrm.inquiry.domain.QInquiry;
import com.team2final.minglecrm.inquiry.domain.QInquiryAction;
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
    public Page<Inquiry> searchByCondition(String keyword, String customerName, String customerPhone, String inquiryTitle, String inquiryContent, LocalDateTime startDate, LocalDateTime endDate, String type, Boolean isReply, ActionStatus actionStatus, Pageable pageable) {
        QInquiry inquiry = QInquiry.inquiry;
        QCustomer customer = QCustomer.customer;
        QInquiryAction inquiryAction = QInquiryAction.inquiryAction;

        BooleanExpression predicate = null;

        if (keyword != null && !keyword.isEmpty()) {
            predicate = inquiry.inquiryTitle.containsIgnoreCase(keyword)
                    .or(inquiry.inquiryContent.containsIgnoreCase(keyword))
                    .or(customer.name.containsIgnoreCase(keyword))
                    .or(customer.phone.containsIgnoreCase(keyword));
        }

        if (customerName != null && !customerName.isEmpty()) {
            BooleanExpression customerNamePredicate = customer.name.containsIgnoreCase(customerName);
            predicate = (predicate == null) ? customerNamePredicate : predicate.and(customerNamePredicate);
        }

        if (customerPhone != null && !customerPhone.isEmpty()) {
            BooleanExpression customerPhonePredicate = customer.phone.containsIgnoreCase(customerPhone);
            predicate = (predicate == null) ? customerPhonePredicate : predicate.and(customerPhonePredicate);
        }

        if (inquiryTitle != null && !inquiryTitle.isEmpty()) {
            BooleanExpression inquiryTitlePredicate = inquiry.inquiryTitle.containsIgnoreCase(inquiryTitle);
            predicate = (predicate == null) ? inquiryTitlePredicate : predicate.and(inquiryTitlePredicate);
        }

        if (inquiryContent != null && !inquiryContent.isEmpty()) {
            BooleanExpression inquiryContentPredicate = inquiry.inquiryContent.containsIgnoreCase(inquiryContent);
            predicate = (predicate == null) ? inquiryContentPredicate : predicate.and(inquiryContentPredicate);
        }

        if (startDate != null && endDate != null) {
            BooleanExpression datePredicate = inquiry.date.between(startDate, endDate);
            predicate = (predicate == null) ? datePredicate : predicate.and(datePredicate);
        }

        if (type != null && !type.isEmpty()) {
            BooleanExpression typePredicate = inquiry.type.equalsIgnoreCase(type);
            predicate = (predicate == null) ? typePredicate : predicate.and(typePredicate);
        }

        if (isReply != null) {
            BooleanExpression isReplyPredicate = inquiry.isReply.eq(isReply);
            predicate = (predicate == null) ? isReplyPredicate : predicate.and(isReplyPredicate);
        }

        if (actionStatus != null) {
            BooleanExpression actionStatusPredicate = inquiryAction.actionStatus.eq(actionStatus);
            predicate = (predicate == null) ? actionStatusPredicate : predicate.and(actionStatusPredicate);
        }

        List<Inquiry> inquiries = queryFactory.selectFrom(inquiry)
                .leftJoin(inquiry.customer, customer)
                .leftJoin(inquiryAction).on(inquiryAction.inquiry.eq(inquiry))
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(inquiry)
                .leftJoin(inquiry.customer, customer)
                .leftJoin(inquiryAction).on(inquiryAction.inquiry.eq(inquiry))
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(inquiries, pageable, total);
    }
}