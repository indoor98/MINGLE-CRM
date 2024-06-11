package com.team2final.minglecrm.persistence.repository.dining.queryDsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.controller.dining.review.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.controller.dining.review.response.QDiningReviewConditionSearchResponse;
import com.team2final.minglecrm.entity.customer.QCustomer;
import com.team2final.minglecrm.entity.dining.QDiningReview;
import com.team2final.minglecrm.entity.dining.QDishReservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiningReviewRepositoryCustomImpl implements DiningReviewRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DiningReviewRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<DiningReviewConditionSearchResponse> searchByExpression(DiningReviewConditionSearchRequest condition, Pageable pageable) {

        QDiningReview diningReview = QDiningReview.diningReview;
        QCustomer customer = QCustomer.customer;
        QDishReservation dishReservation = QDishReservation.dishReservation;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getCustomerName() != null) {
            builder.and(customer.name.eq(condition.getCustomerName()));
        }
        if (condition.getRestaurant() != null) {
            builder.and(dishReservation.restaurant.eq(condition.getRestaurant()));
        }
        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(diningReview.createdDate.between(condition.getStartDate(), condition.getEndDate()));
        }

        List<DiningReviewConditionSearchResponse> response = queryFactory
                .select(new QDiningReviewConditionSearchResponse(
                        customer.name,
                        diningReview.kindnessRating,
                        diningReview.tasteRating,
                        diningReview.cleanlinessRating,
                        diningReview.atmosphereRating,
                        diningReview.review,
                        diningReview.createdDate,
                        dishReservation.restaurant
                ))
                .from(diningReview)
                .join(diningReview.customer, customer)
                .join(diningReview.dishReservation, dishReservation)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(response, pageable, response.size());
    }
}
