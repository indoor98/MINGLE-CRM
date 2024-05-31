package com.team2final.minglecrm.controller.dining.review;

import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.dining.review.response.DiningReviewResponse;
import com.team2final.minglecrm.service.dining.review.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiningReviewController {

    private final DiningReviewService diningReviewService;

    @GetMapping("/api/dining/reviews/{pageno}")
    public ResultResponse<List<DiningReviewResponse>> findAllDiningReview(@PathVariable("pageno") int pageNo){
        return null;
    }
}
