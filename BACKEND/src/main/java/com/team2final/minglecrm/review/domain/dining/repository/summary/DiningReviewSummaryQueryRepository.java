package com.team2final.minglecrm.review.domain.dining.repository.summary;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.review.domain.SummaryType;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.dining.QDiningReviewSummary;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class DiningReviewSummaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiningReviewSummary searchByCondition(DiningReviewSummaryRequest request) {
        QDiningReviewSummary diningReviewSummary = QDiningReviewSummary.diningReviewSummary;

        return queryFactory
                .selectFrom(diningReviewSummary)
                .where(
                        startDateEq(request.getStartDate()),
                        endDateEq(request.getEndDate()),
                        restaurantEq(request.getRestaurant()),
                        summaryTypeEq(request.getSummaryType())
                )
                .orderBy(diningReviewSummary.reviewAmount.desc())
                .fetchOne();
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? QDiningReviewSummary.diningReviewSummary.startDate.eq(startDate) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? QDiningReviewSummary.diningReviewSummary.endDate.eq(endDate) : null;
    }

    private BooleanExpression restaurantEq(String restaurant) {
        return restaurant != null ? QDiningReviewSummary.diningReviewSummary.restaurant.eq(restaurant) : null;
    }

    private BooleanExpression summaryTypeEq(SummaryType summaryType) {
        return summaryType != null ? QDiningReviewSummary.diningReviewSummary.summaryType.eq(summaryType) : null;
    }
}
