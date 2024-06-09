package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryDetailResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryResponse;
import com.team2final.minglecrm.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/inquiries")
public class CustomerInquiryApi {

    private final InquiryService inquiryService;

    // 고객 전체 문의 조회
    @GetMapping()
    public ResponseEntity<ResultResponse<Page<InquiryResponse>>> getInquiriesByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.getInquiriesByCustomerId(customerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "고객 문의 목록 반환 성공", inquiries));
    }

    // 고객 상세 문의 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ResultResponse<InquiryDetailResponse>> getInquiryDetailByCustomerId(
            @PathVariable Long customerId,
            @PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetail = inquiryService.getInquiryDetailByCustomerId(customerId, inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "고객 문의 상세 조회 성공", inquiryDetail));
    }

}