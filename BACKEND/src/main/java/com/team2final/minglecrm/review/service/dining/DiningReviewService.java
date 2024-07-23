package com.team2final.minglecrm.review.service.dining;

import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.domain.dining.repository.review.DiningReviewQueryRepository;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewMetaDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiningReviewService {

    private final DiningReviewQueryRepository diningReviewQueryRepository;
    private final int ROWS_PER_PAGE = 9;

    public List<DiningReviewConditionSearchResponse> searchDiningReviews(DiningReviewConditionSearchRequest condition, int pageNo) {
        Page<DiningReviewConditionSearchResponse> page = diningReviewQueryRepository.searchByExpression(condition, PageRequest.of(pageNo, ROWS_PER_PAGE));
        return new ArrayList<>(page.getContent());
    }

    public DiningReviewMetaDataResponse getDiningReviewMetaData(DiningReviewConditionSearchRequest condition) {
        long rowsNumber = diningReviewQueryRepository.countByExpression(condition);
        return DiningReviewMetaDataResponse.builder()
                .rowsNumber(rowsNumber)
                .pagesNumber((long) Math.ceil((double) rowsNumber / ROWS_PER_PAGE))
                .build();
    }
}
