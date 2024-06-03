package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.reward.response.VoucherHistoryResponse;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/voucher")
public class CustomerVoucherApi {

    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherHistoryResponse>> getCustomerVouchers(@PathVariable Long customerId){
        List<VoucherHistoryResponse> voucherList = voucherService.customerVoucherList(customerId);
        return ResponseEntity.ok(voucherList);
    }

//    @GetMapping("/{voucherId}")
//    public ResponseEntity<VoucherHistoryResponse> getCustomerVouchersDetail(@PathVariable Long customerId){
//        List<VoucherHistoryResponse> voucherList = voucherService.(customerId);
//        return ResponseEntity.ok(voucherList);
//    }


}
