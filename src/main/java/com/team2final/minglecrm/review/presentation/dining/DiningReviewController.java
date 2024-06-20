package com.team2final.minglecrm.review.presentation.dining;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiningReviewController {

    private final DiningReviewService diningReviewService;

    @PostMapping("/api/dining/reviews/{pageno}")
    public ResultResponse<List<DiningReviewConditionSearchResponse>> searchDiningReviews(@PathVariable("pageno") Integer pageNo, @RequestBody DiningReviewConditionSearchRequest condition){
        System.out.println(condition);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", diningReviewService.searchDiningReviews(pageNo, condition));
    }
}
