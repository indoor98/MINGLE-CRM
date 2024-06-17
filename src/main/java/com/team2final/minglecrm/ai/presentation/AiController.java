package com.team2final.minglecrm.ai.presentation;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.ai.dto.request.ReviewSummaryRequest;
import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/api/hotel/review/summary")
    Map<String, String> createHotelReviewSummary(@RequestBody ReviewSummaryRequest request) {
        String answer = aiService.createHotelReviewSummary(request.getStartDate(), request.getSummaryType());
        return Map.of("answer", answer);
    }

    @GetMapping("/api/hotel/review/summary")
    ResultResponse<HotelReviewSummaryResponse> getHotelReviewSummary(@RequestParam SummaryType summaryType) {
        HotelReviewSummaryResponse summary = aiService.getLatestHotelReviewSummary(summaryType);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

    @PostMapping("/api/dining/review/summary")
    Map<String, String> createDiningReviewSummary(@RequestBody ReviewSummaryRequest request) {
        String answer = aiService.createDiningReviewSummary(request.getStartDate(), request.getSummaryType());
        return Map.of("answer", answer);
    }

    @GetMapping("/api/dining/review/summary")
    ResultResponse<DiningReviewSummaryResponse> getDiningReviewSummary(@RequestParam SummaryType summaryType) {
        DiningReviewSummaryResponse summary = aiService.getLatestDiningReviewSummary(summaryType);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

}
