package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.review.domain.hotel.repository.review.HotelReviewQueryRepository;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
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

    private final int ROWS_PER_PAGE = 9;

    private final HotelReviewQueryRepository hotelReviewQueryRepository;

    public List<HotelReviewConditionSearchResponse> searchReviews(HotelReviewConditionSearchRequest condition, int pageNo) {
        Page<HotelReviewConditionSearchResponse> page =  hotelReviewQueryRepository.searchByExpression(condition, PageRequest.of(pageNo, ROWS_PER_PAGE));
        return new ArrayList<>(page.getContent());
    }


    public HotelReviewMetaDataResponse getHotelReviewMetaData(HotelReviewConditionSearchRequest condition) {
        long rowsNumber = hotelReviewQueryRepository.countByExpression(condition);
        return HotelReviewMetaDataResponse.builder()
                .rowsNumber(rowsNumber)
                .pagesNumber((long) Math.ceil((double) rowsNumber /ROWS_PER_PAGE))
                .build();
    }

}
