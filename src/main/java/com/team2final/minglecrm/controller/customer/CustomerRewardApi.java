package com.team2final.minglecrm.controller.customer;

import com.team2final.minglecrm.controller.reward.response.RewardHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.RewardResponse;
import com.team2final.minglecrm.service.customer.CustomerService;
import com.team2final.minglecrm.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<List<RewardHistoryResponse>> getRewardHistory(@PathVariable("customerId") Long customerId) {
        List<RewardHistoryResponse> rewardHistories = rewardService.getRewardHistories(customerId);
        return ResponseEntity.ok(rewardHistories);
    }

    @GetMapping("/{rewardId}")
    public ResponseEntity<RewardResponse> getReward(@PathVariable Long customerId, @PathVariable Long rewardId) {
        RewardResponse reward = rewardService.getReward(customerId);
        return ResponseEntity.ok(reward);
    }

}
