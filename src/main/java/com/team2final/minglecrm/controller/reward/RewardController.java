package com.team2final.minglecrm.controller.reward;

import com.team2final.minglecrm.controller.reward.response.RewardHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.RewardResponse;
import com.team2final.minglecrm.service.reward.RewardService;
import com.team2final.minglecrm.service.reward.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @GetMapping("/{customerId}")
    public ResponseEntity<RewardResponse> getReward(@PathVariable("customerId") Long customerId) {
        RewardResponse reward = rewardService.getReward(customerId);
        return ResponseEntity.ok(reward);
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<RewardHistoryResponse>> getRewardHistory(@PathVariable("customerId") Long customerId) {
        List<RewardHistoryResponse> rewardHistories = rewardService.getRewardHistories(customerId);
        return ResponseEntity.ok(rewardHistories);
    }

}
