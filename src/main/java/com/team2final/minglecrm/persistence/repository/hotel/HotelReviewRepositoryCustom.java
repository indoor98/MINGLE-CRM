package com.team2final.minglecrm.persistence.repository.hotel;

import com.team2final.minglecrm.controller.hotel.review.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelReviewRepositoryCustom {
    Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable);
}
