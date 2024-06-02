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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    public ResponseEntity<List<InquiryResponse>> getAllInquiries() {
        List<InquiryResponse> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryById(@PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetailResponse = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiryDetailResponse);
    }

    @GetMapping("/unanswered")
    public ResponseEntity<List<InquiryResponse>> getUnansweredInquiries() {
        List<InquiryResponse> unansweredInquiries = inquiryService.getUnansweredInquiries();
        return ResponseEntity.ok(unansweredInquiries);
    }

    @GetMapping("/answered")
    public ResponseEntity<List<InquiryResponse>> getAnsweredInquiries() {
        List<InquiryResponse> answeredInquiries = inquiryService.getAnsweredInquiries();
        return ResponseEntity.ok(answeredInquiries);
    }

    @GetMapping("/with-action")
    public ResponseEntity<List<InquiryResponse>> getInquiriesWithAction() {
        List<InquiryResponse> inquiriesWithAction = inquiryService.getInquiriesWithAction();
        return ResponseEntity.ok(inquiriesWithAction);
    }

    @GetMapping("/without-action")
    public ResponseEntity<List<InquiryResponse>> getInquiriesWithoutAction() {
        List<InquiryResponse> inquiriesWithoutAction = inquiryService.getInquiriesWithoutAction();
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
    public ResponseEntity<List<InquiryResponse>> getInquiriesByCustomerId(@PathVariable Long customerId) {
        List<InquiryResponse> inquiries = inquiryService.getInquiriesByCustomerId(customerId);
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/customer/{customerId}/inquiry/{inquiryId}")
    public ResponseEntity<InquiryDetailResponse> getInquiryDetailByCustomerId(
            @PathVariable Long customerId,
            @PathVariable Long inquiryId) {
        InquiryDetailResponse inquiryDetail = inquiryService.getInquiryDetailByCustomerId(customerId, inquiryId);
        return ResponseEntity.ok(inquiryDetail);
    }

}
