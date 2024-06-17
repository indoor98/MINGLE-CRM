package com.team2final.minglecrm.controller.dining.review;

import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.dining.review.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewResponse;
import com.team2final.minglecrm.service.dining.review.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
