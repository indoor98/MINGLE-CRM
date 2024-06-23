package com.team2final.minglecrm.statistics.presentation.purchase;

import com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse;
import com.team2final.minglecrm.statistics.service.purchase.StatisticsPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics/purchase")
public class StatisticsPurchaseController {

    private final StatisticsPurchaseService statisticsPurchaseService;

    @GetMapping("/statistics")
    public List<StatisticsResponse> getStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "false") boolean groupByDishName,
            @RequestParam(required = false, defaultValue = "false") boolean groupByRoomType,
            @RequestParam(required = false, defaultValue = "false") boolean groupByCustomerGender,
            @RequestParam(required = false, defaultValue = "false") boolean groupByCustomerGrade) {
        return statisticsPurchaseService.getStatistics(startDate, endDate, groupByDishName, groupByRoomType, groupByCustomerGender, groupByCustomerGrade);
    }
}
