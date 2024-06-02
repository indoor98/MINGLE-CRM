package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.inquiry.response.InquiryDetailResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryResponse;
import com.team2final.minglecrm.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/inquiries")
public class CustomerInquiryApi {

    private final InquiryService inquiryService;

    // 고객 전체 문의 조회
    @GetMapping()
    public ResponseEntity<List<InquiryResponse>> getAllInquiry(@PathVariable Long customerId) {
        List<InquiryResponse> inquiryResponses = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiryResponses);
    }


    // 고객 상세 문의 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryDetail(
            @PathVariable Long customerId,
            @PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiryDetailResponse);
    }

}