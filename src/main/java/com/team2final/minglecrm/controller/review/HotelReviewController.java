package com.team2final.minglecrm.controller.review;

import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.review.response.HotelReviewResponse;
import com.team2final.minglecrm.controller.review.response.WrapperResponse;
import com.team2final.minglecrm.service.review.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelReviewController {

    private final HotelReviewService hotelReviewService;

    @GetMapping("/api/hotel/reviews/{pageno}")
    public ResponseEntity<ResultResponse<List<HotelReviewResponse>>> getHotelReviewsWithPageNum(@PathVariable("pageno") int pageNo) {
        List<HotelReviewResponse> response = hotelReviewService.findAllHotelReviewsWithPaging(pageNo);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "전체 리뷰 목록 반환 성공", response));
    }

}
