package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.team2final.minglecrm.statistics.dto.response.purchase.*;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseListRepositoryCustom {
    public List<StatisticsDishNameResponse> findStatisticsByDishName(LocalDate startDate, LocalDate endDate);
    public List<StatisticsRoomTypeResponse> findStatisticsByRoomType(LocalDate startDate, LocalDate endDate);
    public List<StatisticsDishNameAndRoomTypeResponse> findStatisticsByDishNameAndRoomType(LocalDate startDate, LocalDate endDate);
    public List<StatisticsGenderResponse> findStatisticsByGender(LocalDate startDate, LocalDate endDate, String gender);
    public List<StatisticsGradeResponse> findStatisticsByGrade(LocalDate startDate, LocalDate endDate, String grade);
}
