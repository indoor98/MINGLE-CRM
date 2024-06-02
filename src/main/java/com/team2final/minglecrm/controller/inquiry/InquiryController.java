package com.team2final.minglecrm.controller.inquiry;

import com.team2final.minglecrm.controller.inquiry.request.InquiryActionRequest;
import com.team2final.minglecrm.controller.inquiry.request.InquiryReplyRequest;
import com.team2final.minglecrm.controller.inquiry.request.UpdateInquiryActionRequest;
import com.team2final.minglecrm.controller.inquiry.request.UpdateInquiryReplyRequest;
import com.team2final.minglecrm.controller.inquiry.response.InquiryActionResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryDetailResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryReplyResponse;
import com.team2final.minglecrm.controller.inquiry.response.InquiryResponse;
import com.team2final.minglecrm.service.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    public ResponseEntity<Page<InquiryResponse>> getAllInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.getAllInquiries(pageable);
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryById(@PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiryDetailResponse);
    }

    @GetMapping("/unanswered")
    public ResponseEntity<Page<InquiryResponse>> getUnansweredInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> unansweredInquiries = inquiryService.getUnansweredInquiries(pageable);
        return ResponseEntity.ok(unansweredInquiries);
    }

    @GetMapping("/answered")
    public ResponseEntity<Page<InquiryResponse>> getAnsweredInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> answeredInquiries = inquiryService.getAnsweredInquiries(pageable);
        return ResponseEntity.ok(answeredInquiries);
    }

    @GetMapping("/with-action")
    public ResponseEntity<Page<InquiryResponse>> getInquiriesWithAction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiriesWithAction = inquiryService.getInquiriesWithAction(pageable);
        return ResponseEntity.ok(inquiriesWithAction);
    }

    @GetMapping("/without-action")
    public ResponseEntity<Page<InquiryResponse>> getInquiriesWithoutAction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiriesWithoutAction = inquiryService.getInquiriesWithoutAction(pageable);
        return ResponseEntity.ok(inquiriesWithoutAction);
    }

    @PostMapping("/reply")
    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<InquiryReplyResponse> replyToInquiry(@RequestBody InquiryReplyRequest request) {
        InquiryReplyResponse replyResponse = inquiryService.replyToInquiry(request);
        return ResponseEntity.ok(replyResponse);
    }

    @PostMapping("/reply/{inquiryReplyId}")
    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<InquiryReplyResponse> updateInquiryReply(@PathVariable Long inquiryReplyId,
                                                                   @RequestBody UpdateInquiryReplyRequest request) {
        InquiryReplyResponse updatedReply = inquiryService.updateInquiryReply(inquiryReplyId, request.getUpdatedReply());
        return ResponseEntity.ok(updatedReply);
    }

    @PostMapping("/action")
    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<InquiryActionResponse> actionToInquiry(@RequestBody InquiryActionRequest request) {
        InquiryActionResponse actionResponse = inquiryService.actionToInquiry(request);
        return ResponseEntity.ok(actionResponse);
    }

    @PostMapping("/action/{inquiryActionId}")
    @PreAuthorize("hasRole('CONSULTANT')")
    public ResponseEntity<InquiryActionResponse> updateInquiryAction(@PathVariable Long inquiryActionId,
                                                                     @RequestBody UpdateInquiryActionRequest request) {
        InquiryActionResponse updateAction = inquiryService.updateInquiryAction(inquiryActionId, request.getUpdateActionContent(), request.getActionStatus());
        return ResponseEntity.ok(updateAction);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<InquiryResponse>> getInquiriesByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.getInquiriesByCustomerId(customerId, pageable);
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/customer/{customerId}/inquiry/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryDetailByCustomerId(
            @PathVariable Long customerId,
            @PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetail = inquiryService.getInquiryDetailByCustomerId(customerId, inquiryId);
        return ResponseEntity.ok(inquiryDetail);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InquiryResponse>> searchInquiries(
            @RequestParam String keyword,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryResponse> inquiries = inquiryService.searchInquiries(keyword, startDate, endDate, pageable);
        return ResponseEntity.ok(inquiries);
    }

}
