package com.team2final.minglecrm.persistence.repository.reward;

import com.team2final.minglecrm.entity.reward.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findByCustomerId(Long customerId);
}
