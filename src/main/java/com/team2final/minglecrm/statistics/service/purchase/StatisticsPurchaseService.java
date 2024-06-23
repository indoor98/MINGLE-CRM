package com.team2final.minglecrm.statistics.service.purchase;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.statistics.domain.PurchaseList;
import com.team2final.minglecrm.statistics.domain.QPurchaseList;
import com.team2final.minglecrm.statistics.domain.repository.purchase.PurchaseListRepository;
import com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsPurchaseService {

    private final PurchaseListRepository purchaseListRepository;

    public List<StatisticsResponse> getStatistics(LocalDate startDate, LocalDate endDate,
                                                  boolean groupByDishName, boolean groupByRoomType,
                                                  boolean groupByCustomerGender, boolean groupByCustomerGrade) {
        return purchaseListRepository.findStatistics(startDate, endDate, groupByDishName, groupByRoomType, groupByCustomerGender, groupByCustomerGrade);
    }
}
