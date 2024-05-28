package com.team2final.minglecrm.controller.reward;

import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherCreateResponse;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping("/new")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<VoucherCreateResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherCreateResponse createdVoucher = voucherService.createVoucher(request);
        return ResponseEntity.ok(createdVoucher);
    }
}
