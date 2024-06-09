package com.team2final.minglecrm.controller.reward;


import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.*;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<ResultResponse<VoucherResponse>> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse createdVoucher = voucherService.saveVoucher(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 생성 성공", createdVoucher));
    }

    // 생성된 모든 바우처 목록 가져오기
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherResponse>>> getAllVouchers(){
        List<VoucherResponse> voucherList = voucherService.getAllVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 승인 전&후 바우처 목록 가져오기
    @GetMapping("histories") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVoucherHistories(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllVoucherHistories();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
    }

    // 승인전 바우처 목록 가져오기
    @GetMapping("/requested") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRequestedVouchers(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllRequestedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 요청된 바우처 전체 조회 성공", voucherList));
    }

    // 사용자별 바우처 리스트 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVouchers(@PathVariable("customerId") Long customerId){
        List<VoucherHistoryResponse> customerVoucherList = voucherService.getCustomerVouchers(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 목록 조회 성공", customerVoucherList));
    }

    // 사용자별 바우처 상세 조회
    @GetMapping("/{customerId}/{voucherId}")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> getVoucher(@PathVariable Long customerId, @PathVariable Long voucherId){
        VoucherHistoryResponse voucherHistory = voucherService.getCustomerVoucher(customerId, voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 상세 조회 성공", voucherHistory));
    }

    @PostMapping("/request/{voucherId}")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<VoucherRequestResponse>> requestVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherRequestResponse voucherRequestResponse = voucherService.requestVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 요청 성공", voucherRequestResponse));
    }

    @PostMapping("/approval/{voucherId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherApprovalResponse>> approveVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherApprovalResponse voucherApprovalResponse = voucherService.approveVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherApprovalResponse));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<List<VoucherStatusResponse>>> getVouchersStatus(){
        List<VoucherStatusResponse> voucherStatusList = voucherService.getVouchersStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 요청/승인 상태 조회 성공", voucherStatusList));
    }
}