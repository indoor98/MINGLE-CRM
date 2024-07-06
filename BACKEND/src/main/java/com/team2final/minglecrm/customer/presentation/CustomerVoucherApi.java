package com.team2final.minglecrm.customer.presentation;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.voucher.dto.response.VoucherHistoryResponse;
import com.team2final.minglecrm.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    // 사용자별 바우처 리스트 조회
    @GetMapping()
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVouchers(@PathVariable("customerId") Long customerId){
        List<VoucherHistoryResponse> customerVoucherList = voucherService.getCustomerVouchers(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 목록 조회 성공", customerVoucherList));
    }

    // 사용자별 바우처 상세 조회
    @GetMapping("/{voucherId}")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> getVoucher(@PathVariable Long customerId, @PathVariable Long voucherId){
        VoucherHistoryResponse voucherHistory = voucherService.getCustomerVoucher(customerId, voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 상세 조회 성공", voucherHistory));
    }


}