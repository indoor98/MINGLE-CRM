package com.team2final.minglecrm.persistence.repository.dining.queryDsl;

import com.team2final.minglecrm.controller.dining.review.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewConditionSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiningReviewRepositoryCustom {

    Page<DiningReviewConditionSearchResponse> searchByExpression(DiningReviewConditionSearchRequest condition, Pageable pageable);
}
