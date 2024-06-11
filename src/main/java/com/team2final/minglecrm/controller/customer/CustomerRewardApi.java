package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.ResultResponse;
import com.team2final.minglecrm.controller.reward.response.RewardHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.RewardResponse;
import com.team2final.minglecrm.service.reward.RewardService;
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
@RequestMapping("/api/v1/customers/{customerId}/rewards")
public class CustomerRewardApi {

    private final RewardService rewardService;

//    @GetMapping("/{rewardId}")
//    public ResponseEntity<RewardResponse> getReward(@PathVariable Long customerId, @PathVariable Long rewardId) {
//        RewardResponse reward = rewardService.getReward(customerId);
//        return ResponseEntity.ok(reward);
//    }

    @GetMapping()
    public ResponseEntity<ResultResponse<RewardResponse>> getReward(@PathVariable("customerId") Long customerId) {
        RewardResponse reward = rewardService.getReward(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 리워드 조회 성공", reward));
    }

    @GetMapping("/history")
    public ResponseEntity<ResultResponse<List<RewardHistoryResponse>>> getRewardHistory(@PathVariable("customerId") Long customerId) {
        List<RewardHistoryResponse> rewardHistories = rewardService.getRewardHistories(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "사용자별 리워드 히스토리 조회 성공", rewardHistories));
    }

}