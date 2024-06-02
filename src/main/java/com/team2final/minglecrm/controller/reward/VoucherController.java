package com.team2final.minglecrm.controller.reward;


import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.*;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse createdVoucher = voucherService.saveVoucher(request);
        return ResponseEntity.ok(createdVoucher);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VoucherResponse>> listVouchers(){
        List<VoucherResponse> voucherList = voucherService.getAllVouchers();
        return ResponseEntity.ok(voucherList);
    }

    // 사용자별 바우처 리스트 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<List<VoucherHistoryResponse>> getVouchers(@PathVariable("customerId") Long customerId){
        List<VoucherHistoryResponse> voucherList = voucherService.getCustomerVouchers(customerId);
        return ResponseEntity.ok(voucherList);
    }

    // 사용자별 바우처 상세 조회
    @GetMapping("/{customerId}/{voucherId}")
    public ResponseEntity<VoucherHistoryResponse> getVoucher(@PathVariable Long customerId, Long voucherId){
        VoucherHistoryResponse voucherHistory = voucherService.getCustomerVoucher(customerId, voucherId);
        return ResponseEntity.ok(voucherHistory);
    }

    @PostMapping("/request/{voucherId}")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<VoucherRequestResponse> requestVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherRequestResponse voucherRequestResponse = voucherService.requestVoucher(voucherId);
        return ResponseEntity.ok(voucherRequestResponse);
    }

    @PostMapping("/approval/{voucherId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<VoucherApprovalResponse> approveVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherApprovalResponse voucherApprovalResponse = voucherService.approveVoucher(voucherId);
        return ResponseEntity.ok(voucherApprovalResponse);
    }

    @GetMapping("/status/marketer")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<List<VoucherStatusResponse>> getVouchersStatus(){
        List<VoucherStatusResponse> voucherStatusList = voucherService.getVouchersStatus();
        return ResponseEntity.ok(voucherStatusList);
    }
}
