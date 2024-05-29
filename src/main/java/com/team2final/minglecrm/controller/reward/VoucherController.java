package com.team2final.minglecrm.controller.reward;


import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherApprovalResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherRequestResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherResponse;
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

    @PostMapping("/request/{voucherId}")
    public ResponseEntity<VoucherRequestResponse> requestVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherRequestResponse voucherRequestResponse = voucherService.requestVoucher(voucherId);
        return ResponseEntity.ok(voucherRequestResponse);
    }

    @PostMapping("/approval/{voucherId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<VoucherApprovalResponse> approveVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherApprovalResponse voucherApprovalResponse = voucherService.appoveVoucher(voucherId);
        return ResponseEntity.ok(voucherApprovalResponse);
    }
}
