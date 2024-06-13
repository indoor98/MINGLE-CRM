package com.team2final.minglecrm.controller.reward;


import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.request.VoucherRejectRequest;
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

    // 매니저 권한일 때

    // 1. 승인 요청된 바우처 목록
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRequestedVouchers(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllRequestedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 요청된 바우처 전체 조회 성공", voucherList));
    }

    // 2. 바우처 승인
    @PostMapping("/approval/{voucherId}") // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherApprovalResponse>> approveVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherApprovalResponse voucherApprovalResponse = voucherService.approveVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherApprovalResponse));
    }

    // 3. 바우처 승인 거절
    @PostMapping("/rejection/{voucherId}") // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> rejectVoucher(@PathVariable("voucherId") Long voucherId, @RequestBody VoucherRejectRequest request) {
        VoucherHistoryResponse voucherRejectResponse = voucherService.rejectVoucher(voucherId, request.getReason());
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherRejectResponse));
    }

    // 4. 승인 완료된 바우처 목록
    @GetMapping("/approved")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getApprovedVouchers(){
        List<VoucherHistoryResponse> approvedVouchers = voucherService.getApprovedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 목록 확인", approvedVouchers));
    }


    // 5. 승인 거절된 바우처 목록
    @GetMapping("/rejected") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRejectedVouchers(){
        List<VoucherHistoryResponse> voucherList = voucherService.getRejectedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
    }


    // 6. 모든 바우처(히스토리) 목록
    @GetMapping("/histories") //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVoucherHistories(){
        List<VoucherHistoryResponse> voucherList = voucherService.getAllVoucherHistories();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
    }

    // 7. 리워드 전환된 바우처 목록 ?

    // 8. (요청된) 바우처 상세
    @GetMapping("/{voucherId}")
    public ResponseEntity<ResultResponse<VoucherHistoryResponse>> getVoucher(@PathVariable Long voucherId){
        VoucherHistoryResponse voucherHistory = voucherService.getVoucherHistory(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 사용 가능 바우처 상세 조회 성공", voucherHistory));
    }





    // 마케터 권한일 때

    // 1. 바우처 생성
    @PostMapping // @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<VoucherResponse>> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse createdVoucher = voucherService.saveVoucher(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 생성 성공", createdVoucher));
    }

    // 2. 승인 요청 전 바우처 목록
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherResponse>>> getNotRequestedVouchers(){
        List<VoucherResponse> voucherList = voucherService.getNotRequestedVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 3. 바우처 승인 요청
    // voucher - isRequested : True로
    // voucherHistory - requestedDate : now로


    // 4. 승인 요청 전 바우처 삭제

    // 5. 승인 요청한 바우처 목록
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherResponse>>> getRequestedVouchersByMarketer(){
//        List<VoucherResponse> voucherList = voucherService.get();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 6. 승인 완료된 바우처 목록
    // 7. 승인 거절된 바우처 목록




//    @PostMapping // @PreAuthorize("hasRole('MARKETER')")
//    public ResponseEntity<ResultResponse<VoucherResponse>> createVoucher(@RequestBody VoucherCreateRequest request) {
//        VoucherResponse createdVoucher = voucherService.saveVoucher(request);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 생성 성공", createdVoucher));
//    }

    // 생성된 모든 바우처 목록 가져오기
    @GetMapping //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResultResponse<List<VoucherResponse>>> getAllVouchers(){
        List<VoucherResponse> voucherList = voucherService.getAllVouchers();
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "생성된 바우처 전체 조회 성공", voucherList));
    }

    // 승인 전&후 바우처 목록 가져오기
//    @GetMapping("/histories") //@PreAuthorize("hasRole('MANAGER')")
//    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getVoucherHistories(){
//        List<VoucherHistoryResponse> voucherList = voucherService.getAllVoucherHistories();
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 전&후 바우처 전체 조회 성공", voucherList));
//    }

//    // 승인전 바우처 목록 가져오기
//    @GetMapping("/requested") //@PreAuthorize("hasRole('MANAGER')")
//    public ResponseEntity<ResultResponse<List<VoucherHistoryResponse>>> getRequestedVouchers(){
//        List<VoucherHistoryResponse> voucherList = voucherService.getAllRequestedVouchers();
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "승인 요청된 바우처 전체 조회 성공", voucherList));
//    }

    // 사용자별 바우처(히스토리) 리스트 조회
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

    @PostMapping("/request/{voucherId}") // @PreAuthorize("hasRole('MARKETER')")
    public ResponseEntity<ResultResponse<VoucherRequestResponse>> requestVoucher(@PathVariable("voucherId") Long voucherId) {
        VoucherRequestResponse voucherRequestResponse = voucherService.requestVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 요청 성공", voucherRequestResponse));
    }

//    @PostMapping("/approval/{voucherId}") // @PreAuthorize("hasRole('MANAGER')")
//    public ResponseEntity<ResultResponse<VoucherApprovalResponse>> approveVoucher(@PathVariable("voucherId") Long voucherId) {
//        VoucherApprovalResponse voucherApprovalResponse = voucherService.approveVoucher(voucherId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 승인 성공", voucherApprovalResponse));
//    }

//    @GetMapping("/status")
//    @PreAuthorize("hasRole('MARKETER')")
//    public ResponseEntity<ResultResponse<List<VoucherStatusResponse>>> getVouchersStatus(){
//        List<VoucherStatusResponse> voucherStatusList = voucherService.getVouchersStatus();
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "바우처 요청/승인 상태 조회 성공", voucherStatusList));
//    }

    // 자신(마케터)이 생성한 바우처 목록 보기
//    @GetMapping("/")
}