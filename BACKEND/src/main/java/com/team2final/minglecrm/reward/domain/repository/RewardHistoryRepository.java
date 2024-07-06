package com.team2final.minglecrm.reward.domain.repository;


import com.team2final.minglecrm.reward.domain.RewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {
    @Query("SELECT rh FROM RewardHistory rh WHERE rh.reward.customer.id = :customerId")
    List<RewardHistory> findRewardHistoriesByCustomerId(@Param("customerId") Long customerId);
}
