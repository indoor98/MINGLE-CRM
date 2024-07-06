package com.team2final.minglecrm.reward.domain.repository;

import com.team2final.minglecrm.reward.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findByCustomerId(Long customerId);
}
