package com.team2final.minglecrm.ai.presentation;

import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.SummaryType;
import com.team2final.minglecrm.review.service.ai.AiService;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final DiningReviewService diningReviewService;

    @PostMapping("/api/dining/review/summary")
    ResultResponse<String> createDiningReviewSummary(@RequestBody DiningReviewSummary request) {
        String restaurant = request.getRestaurant();
        if (request.getRestaurant().equals("All")){
            restaurant = null;
        }

        JoinedReviews joinedDiningReviews = diningReviewService.getJoinedDiningReviews(request.getStartDate(), request.getEndDate(), restaurant);

        if (joinedDiningReviews == null) {
            return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), "리뷰 데이터 없음", null);
        }

        String answer = aiService.createDiningReviewSummary(joinedDiningReviews, request.getSummaryType(), request.getRestaurant());
        return new ResultResponse<>(HttpStatus.CREATED.value(), "success", answer);
    }
    @GetMapping("/api/dining/review/summary")
    ResultResponse<DiningReviewSummaryResponse> getDiningReviewSummary(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam SummaryType summaryType,
            @RequestParam String restaurant
    ) {
        DiningReviewSummaryResponse summary = aiService.getDiningReviewSummaryByPeriod(startDate, endDate, summaryType, restaurant);

        if (summary == null) {
            return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
        }

        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

}
