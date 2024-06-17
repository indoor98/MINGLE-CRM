package com.team2final.minglecrm.persistence.repository.hotel.queryDsl;

import com.team2final.minglecrm.controller.ai.vo.HotelReviewForSummary;
import com.team2final.minglecrm.controller.hotel.review.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.entity.hotel.HotelReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface HotelReviewRepositoryCustom {
    Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable);
    List<HotelReviewForSummary> findAllByStartDateCondition(LocalDateTime startDate);

}
