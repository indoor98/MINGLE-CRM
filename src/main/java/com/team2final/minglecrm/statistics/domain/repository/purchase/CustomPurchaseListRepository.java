package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse;

import java.time.LocalDate;
import java.util.List;

public interface CustomPurchaseListRepository {

    List<StatisticsResponse> findStatistics(LocalDate startDate, LocalDate endDate,
                                            boolean groupByDishName, boolean groupByRoomType,
                                            boolean groupByCustomerGender, boolean groupByCustomerGrade);
}
