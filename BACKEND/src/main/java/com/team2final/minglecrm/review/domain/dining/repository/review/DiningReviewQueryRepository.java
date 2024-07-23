package com.team2final.minglecrm.review.domain.dining.repository.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.dining.QDishReservation;
import com.team2final.minglecrm.review.domain.dining.QDiningReview;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.dining.response.QDiningReviewConditionSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiningReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<DiningReviewConditionSearchResponse> searchByExpression(DiningReviewConditionSearchRequest condition, Pageable pageable) {
        List<DiningReviewConditionSearchResponse> response = getSearchQuery(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = getCountQuery(condition).fetchOne();

        return new PageImpl<>(response, pageable, total != null ? total : 0L);
    }

    public List<DiningReviewConditionSearchResponse> searchByExpression(DiningReviewConditionSearchRequest condition) {
        return getSearchQuery(condition).fetch();
    }

    public long countByExpression(DiningReviewConditionSearchRequest condition) {
        Long count = getCountQuery(condition).fetchOne();
        return count != null ? count : 0;
    }

    public JPAQuery<DiningReviewConditionSearchResponse> getSearchQuery(DiningReviewConditionSearchRequest condition) {

        QDiningReview diningReview = QDiningReview.diningReview;
        QCustomer customer = QCustomer.customer;
        QDishReservation dishReservation = QDishReservation.dishReservation;

        return queryFactory
                .select(new QDiningReviewConditionSearchResponse(
                        customer.name,
                        diningReview.kindnessRating,
                        diningReview.tasteRating,
                        diningReview.cleanlinessRating,
                        diningReview.atmosphereRating,
                        diningReview.review,
                        diningReview.createdTime,
                        dishReservation.restaurant
                ))
                .from(diningReview)
                .join(diningReview.customer, customer)
                .join(diningReview.dishReservation, dishReservation)
                .where(
                        customerNameEq(condition.getCustomerName()),
                        restaurantEq(condition.getRestaurant()),
                        createdTimeBetween(condition.getStartDate(), condition.getEndDate())
                );
    }

    public JPAQuery<Long> getCountQuery(DiningReviewConditionSearchRequest condition) {
        QDiningReview diningReview = QDiningReview.diningReview;
        QDishReservation dishReservation = QDishReservation.dishReservation;
        QCustomer customer = QCustomer.customer;


        return queryFactory
                .select(diningReview.count().longValue())
                .from(diningReview)
                .join(diningReview.customer, customer)
                .join(diningReview.dishReservation, dishReservation)
                .where(
                        customerNameEq(condition.getCustomerName()),
                        restaurantEq(condition.getRestaurant()),
                        createdTimeBetween(condition.getStartDate(), condition.getEndDate())
                );
    }

    public BooleanExpression customerNameEq(String customerName) {
        return customerName != null ? QCustomer.customer.name.eq(customerName) : null;
    }

    public BooleanExpression restaurantEq(String restaurant) {
        return restaurant != null ? QDishReservation.dishReservation.restaurant.eq(restaurant) : null;
    }

    public BooleanExpression createdTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (startDate != null) && (endDate != null) ? QDiningReview.diningReview.createdTime.between(startDate, endDate) : null;
    }
}
