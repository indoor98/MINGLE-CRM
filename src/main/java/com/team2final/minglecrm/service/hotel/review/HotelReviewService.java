package com.team2final.minglecrm.service.hotel.review;

import com.team2final.minglecrm.controller.hotel.review.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewResponse;
import com.team2final.minglecrm.entity.hotel.HotelReview;
import com.team2final.minglecrm.persistence.repository.hotel.HotelReviewRepository;
import com.team2final.minglecrm.persistence.repository.hotel.HotelReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;
    private final HotelReviewRepositoryCustom hotelReviewRepositoryCustom;

    public List<HotelReviewResponse> findAllHotelReviewsWithPaging(int pageNo) {
        Page<HotelReview> hotelReviewPage = hotelReviewRepository.findAll(PageRequest.of(pageNo, 9));


        List<HotelReviewResponse> response = new ArrayList<>();

        for(HotelReview hotelReview: hotelReviewPage.getContent()) {
            response.add(HotelReviewResponse.of(hotelReview));
        }
        return response;
    }

    public List<HotelReviewConditionSearchResponse> searchReviews(HotelReviewConditionSearchRequest request, int pageNo) {

        Page<HotelReviewConditionSearchResponse> page =  hotelReviewRepositoryCustom.searchByExpression(request, PageRequest.of(pageNo, 9));
        List<HotelReviewConditionSearchResponse> response = new ArrayList<>();

        for(HotelReviewConditionSearchResponse hotelReview : page.getContent() ) {
            response.add(hotelReview);
        }

        return response;
    }
}
