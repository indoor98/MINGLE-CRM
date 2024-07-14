package com.team2final.minglecrm.review.presentation.hotel;

import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewSummaryRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.review.service.ai.AiService;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewCombinedResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import com.team2final.minglecrm.review.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelReviewApi {

    private final HotelReviewService hotelReviewService;
    private final AiService aiService;

    @GetMapping("/api/hotel/review/{page}")
    public ResultResponse<HotelReviewCombinedResponse> getReviews(
            @PathVariable("page") int page,
            @ModelAttribute HotelReviewConditionSearchRequest request) {
        HotelReviewMetaDataResponse metaData = hotelReviewService.getHotelReviewMetaData(request);
        List<HotelReviewConditionSearchResponse> reviews = hotelReviewService.searchReviews(request, page);
        HotelReviewCombinedResponse combinedResponse = new HotelReviewCombinedResponse(metaData, reviews);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", combinedResponse);
    }

    @GetMapping("/api/hotel/review/summary")
    ResultResponse<HotelReviewSummaryResponse> getHotelReviewSummary(
            @ModelAttribute HotelReviewSummaryRequest request) {
        try {
            HotelReviewSummaryResponse response = aiService.getHotelReviewSummary(request)
                    .orElseGet(() -> aiService.createHotelReviewSummary(request));

            return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
        } catch (Exception e) {
            return new ResultResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
        }
    }

}