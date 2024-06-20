package com.team2final.minglecrm.ai.presentation;

import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.ai.dto.request.ReviewSummaryRequest;
import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.review.domain.dining.DiningReview;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewRepository;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.ai.service.AiService;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import com.team2final.minglecrm.review.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final HotelReviewService hotelReviewService;
    private final DiningReviewService diningReviewService;

    @PostMapping("/api/hotel/review/summary")
    Map<String, String> createHotelReviewSummary(@RequestBody ReviewSummaryRequest request) {
        JoinedReviews joinedHotelReviews = hotelReviewService.getJoinedHotelReviews(request.getStartDate(), request.getEndDate());
        String answer = aiService.createHotelReviewSummary(joinedHotelReviews, request.getSummaryType());
        return Map.of("answer", answer);
    }

    @GetMapping("/api/hotel/review/summary")
    ResultResponse<HotelReviewSummaryResponse> getHotelReviewSummary(@RequestParam SummaryType summaryType) {
        HotelReviewSummaryResponse summary = aiService.getLatestHotelReviewSummary(summaryType);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

    @PostMapping("/api/dining/review/summary")
    Map<String, String> createDiningReviewSummary(@RequestBody ReviewSummaryRequest request) {
        JoinedReviews joinedDiningReviews = diningReviewService.getJoinedDiningReviews(request.getStartDate(), request.getEndDate());
        String answer = aiService.createDtiningReviewSummary(joinedDiningReviews, request.getSummaryType());
        return Map.of("answer", answer);
    }

    @GetMapping("/api/dining/review/summary")
    ResultResponse<DiningReviewSummaryResponse> getDiningReviewSummary(@RequestParam SummaryType summaryType) {
        DiningReviewSummaryResponse summary = aiService.getLatestDiningReviewSummary(summaryType);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

}
