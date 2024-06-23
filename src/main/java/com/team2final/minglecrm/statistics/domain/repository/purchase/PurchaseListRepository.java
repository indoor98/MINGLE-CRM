package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.team2final.minglecrm.statistics.domain.PurchaseList;
import com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Long>, CustomPurchaseListRepository {

}
