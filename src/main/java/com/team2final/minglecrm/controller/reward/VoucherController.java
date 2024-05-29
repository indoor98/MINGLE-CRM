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

    @PostMapping("/new")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse createdVoucher = voucherService.createVoucher(request);
        return ResponseEntity.ok(createdVoucher);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VoucherResponse>> getAllVouchers(){
        List<VoucherResponse> voucherList = voucherService.voucherList();
        return ResponseEntity.ok(voucherList);
    }

    // 사용자별 바우처 리스트 조회
    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<VoucherHistoryResponse>> getVouchers(@PathVariable("customerId") Long customerId){
        List<VoucherHistoryResponse> voucherList = voucherService.customerVoucherList(customerId);
        return ResponseEntity.ok(voucherList);
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

    @GetMapping("/list/requested/marketer")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<List<VoucherStatusResponse>> voucherStatusList(){
        List<VoucherStatusResponse> voucherStatusList = voucherService.voucherStatusList();
        return ResponseEntity.ok(voucherStatusList);
    }
    // 요청전/승인전/승인완료 상태 리스트를 보려면 voucher랑 voucherHistory 조인해서 가져와야함 <- 수정하기
}
