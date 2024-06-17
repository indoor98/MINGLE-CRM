package com.team2final.minglecrm.service.dining.review;

import com.team2final.minglecrm.controller.dining.review.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewResponse;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.entity.dining.DiningReview;
import com.team2final.minglecrm.persistence.repository.dining.DiningReviewRepository;
import com.team2final.minglecrm.persistence.repository.dining.queryDsl.DiningReviewRepositoryCustom;
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
    private final DiningReviewRepositoryCustom diningReviewRepositoryCustom;

    public List<DiningReviewResponse> findAllDiningReviewsWithPaging(Integer pageNo) {
        Page<DiningReview> diningReviewPage = diningReviewRepository.findAll(PageRequest.of(pageNo, 9));

        List<DiningReviewResponse> response = new ArrayList<>();
        for (DiningReview diningReview : diningReviewPage) {
            response.add(DiningReviewResponse.of(diningReview));
        }
        return response;
    }

    public List<DiningReviewConditionSearchResponse> searchDiningReviews(Integer pageNo, DiningReviewConditionSearchRequest condition) {
        Page<DiningReviewConditionSearchResponse> page =  diningReviewRepositoryCustom.searchByExpression(condition, PageRequest.of(pageNo, 9));
        List<DiningReviewConditionSearchResponse> response = new ArrayList<>();

        for(DiningReviewConditionSearchResponse diningReview : page.getContent() ) {
            response.add(diningReview);
        }

        return response;
    }

}
