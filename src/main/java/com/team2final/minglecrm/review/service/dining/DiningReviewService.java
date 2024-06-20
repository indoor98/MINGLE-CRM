package com.team2final.minglecrm.review.service.dining;

import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewResponse;
import com.team2final.minglecrm.review.domain.dining.DiningReview;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewRepository;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewQueryDslRepository;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewMetaDataResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiningReviewService {

    private final DiningReviewRepository diningReviewRepository;
    private final int ROWS_PER_PAGE = 9;

    public List<DiningReviewResponse> findAllDiningReviewsWithPaging(Integer pageNo) {
        Page<DiningReview> diningReviewPage = diningReviewRepository.findAll(PageRequest.of(pageNo, 9));

        List<DiningReviewResponse> response = new ArrayList<>();
        for (DiningReview diningReview : diningReviewPage) {
            response.add(DiningReviewResponse.of(diningReview));
        }
        return response;
    }

    public List<DiningReviewConditionSearchResponse> searchDiningReviews(Integer pageNo, DiningReviewConditionSearchRequest condition) {
        Page<DiningReviewConditionSearchResponse> page =  diningReviewRepository.searchByExpression(condition, PageRequest.of(pageNo, ROWS_PER_PAGE));

        return new ArrayList<>(page.getContent());
    }

    public DiningReviewMetaDataResponse getDiningReviewMetaData() {
        long rowsNumber = diningReviewRepository.count();
        DiningReviewMetaDataResponse diningReviewMetaDataResponse =
                DiningReviewMetaDataResponse.builder()
                        .rowsNumber(rowsNumber)
                        .pagesNumber((long) Math.ceil(rowsNumber/ROWS_PER_PAGE))
                        .build();
        return diningReviewMetaDataResponse;
    }

}
