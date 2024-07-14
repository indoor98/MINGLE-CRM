package com.team2final.minglecrm.review.domain.hotel.repository.summary;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.review.domain.hotel.Hotel;
import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.QHotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewSummaryRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewSummaryResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelReviewSummaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HotelReviewSummary searchByCondition(HotelReviewSummaryRequest request) {
        QHotelReviewSummary hotelReviewSummary = QHotelReviewSummary.hotelReviewSummary;

        return queryFactory
                .selectFrom(hotelReviewSummary)
                .where(
                        startDateEq(request.getStartDate()),
                        endDateEq(request.getEndDate()),
                        hotelEq(request.getHotel()),
                        summaryTypeEq(request.getSummaryType())
                )
                .orderBy(hotelReviewSummary.reviewAmount.desc())
                .fetchOne();
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? QHotelReviewSummary.hotelReviewSummary.startDate.eq(startDate) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? QHotelReviewSummary.hotelReviewSummary.endDate.eq(endDate) : null;
    }

    private BooleanExpression hotelEq(Hotel hotel) {
        return hotel != null ? QHotelReviewSummary.hotelReviewSummary.hotel.eq(hotel) : null;
    }

    private BooleanExpression summaryTypeEq(SummaryType summaryType) {
        return summaryType != null ? QHotelReviewSummary.hotelReviewSummary.summaryType.eq(summaryType) : null;
    }

}
