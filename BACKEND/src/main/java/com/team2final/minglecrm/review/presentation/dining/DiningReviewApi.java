package com.team2final.minglecrm.review.presentation.dining;

import com.team2final.minglecrm.review.dto.dining.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewSummaryRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewCombinedResponse;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewMetaDataResponse;
import com.team2final.minglecrm.review.service.ai.AiService;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiningReviewApi {

    private final DiningReviewService diningReviewService;
    private final AiService aiService;

    @GetMapping("/api/dining/review/{page}")
    ResultResponse<DiningReviewCombinedResponse> getReviews(
            @PathVariable("page") int page,
            @ModelAttribute DiningReviewConditionSearchRequest request) {
        DiningReviewMetaDataResponse metaData = diningReviewService.getDiningReviewMetaData(request);
        List<DiningReviewConditionSearchResponse> reviews = diningReviewService.searchDiningReviews(request, page);
        DiningReviewCombinedResponse combinedResponse = new DiningReviewCombinedResponse(metaData, reviews);
        return ResultResponse.success(HttpStatus.OK.value(), "success", combinedResponse);
    }

    @GetMapping("/api/dining/review/summary")
    ResultResponse<DiningReviewSummaryResponse> getDiningReviewSummary(
            @ModelAttribute DiningReviewSummaryRequest request) {
        try {
            DiningReviewSummaryResponse response = aiService.getDiningReviewSummary(request)
                    .orElseGet(() -> aiService.createDiningReviewSummary(request));

            return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
        } catch (Exception e) {
            return new ResultResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
        }
    }

}
