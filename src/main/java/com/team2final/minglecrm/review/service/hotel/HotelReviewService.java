package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import com.team2final.minglecrm.review.domain.hotel.repository.HotelReviewRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.HotelReviewQueryDslRepository;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
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
    private final int ROWS_PER_PAGE = 9;

    public List<HotelReviewConditionSearchResponse> searchReviews(HotelReviewConditionSearchRequest condition, int pageNo) {

        Page<HotelReviewConditionSearchResponse> page =  hotelReviewRepository.searchByExpression(condition, PageRequest.of(pageNo, ROWS_PER_PAGE));
        List<HotelReviewConditionSearchResponse> response = new ArrayList<>();

        for(HotelReviewConditionSearchResponse hotelReview : page.getContent() ) {
            response.add(hotelReview);
        }

        return response;
    }

    public String getEmbeddedReviews() {
        List<HotelReview> hotelReviewList = hotelReviewRepository.findAll();
        String response = "";

        for (int i=0; i < hotelReviewList.size() ; i++) {
            response += i + " 번째 리뷰 : " + hotelReviewList.get(i).getComment() + "\n";
        }

        return response;
    }

    public HotelReviewMetaDataResponse getHotelReviewMetaData() {
        long rowsNumber = hotelReviewRepository.count();
        HotelReviewMetaDataResponse hotelReviewMetaDataResponse =
                HotelReviewMetaDataResponse.builder()
                        .rowsNumber(rowsNumber)
                        .pagesNumber((long) Math.ceil(rowsNumber/ROWS_PER_PAGE))
                        .build();
        return hotelReviewMetaDataResponse;
    }


}
