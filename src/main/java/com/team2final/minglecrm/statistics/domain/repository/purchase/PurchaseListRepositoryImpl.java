package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.statistics.dto.response.purchase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.team2final.minglecrm.statistics.domain.QPurchaseList.purchaseList;

@RequiredArgsConstructor
@Repository
public class PurchaseListRepositoryImpl implements PurchaseListRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatisticsDishNameResponse> findStatisticsByDishName(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(new QStatisticsDishNameResponse(
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.dishName.count().as("count")
                ))
                .from(purchaseList)
                .where(startDate != null && endDate != null ? purchaseList.purchaseDate.between(startDate, endDate) : null)
                .groupBy(
                        purchaseList.consumeType,
                        purchaseList.dishName
                )
                .orderBy(purchaseList.dishName.count().desc())
                .fetch();
    }

    @Override
    public List<StatisticsRoomTypeResponse> findStatisticsByRoomType(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(new QStatisticsRoomTypeResponse(
                        purchaseList.consumeType,
                        purchaseList.roomType,
                        purchaseList.roomType.count().as("count")
                ))
                .from(purchaseList)
                .where(startDate != null && endDate != null ? purchaseList.purchaseDate.between(startDate, endDate) : null)
                .groupBy(
                        purchaseList.consumeType,
                        purchaseList.roomType
                )
                .orderBy(purchaseList.roomType.count().desc())
                .fetch();
    }

    @Override
    public List<StatisticsDishNameAndRoomTypeResponse> findStatisticsByDishNameAndRoomType(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(new QStatisticsDishNameAndRoomTypeResponse(
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType,
                        purchaseList.dishName.count().as("dishNameCount"),
                        purchaseList.roomType.count().as("roomTypeCount")
                ))
                .from(purchaseList)
                .where(startDate != null && endDate != null ? purchaseList.purchaseDate.between(startDate, endDate) : null)
                .groupBy(
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType
                )
                .orderBy(purchaseList.dishName.count().desc())
                .fetch();
    }

    @Override
    public List<StatisticsGenderResponse> findStatisticsByGender(LocalDate startDate, LocalDate endDate, String gender) {
        return queryFactory
                .select(new QStatisticsGenderResponse(
                        purchaseList.customerGender,
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType,
                        purchaseList.dishName.count().as("dishNameCount"),
                        purchaseList.roomType.count().as("roomTypeCount")
                ))
                .from(purchaseList)
                .where(startDate != null && endDate != null ? purchaseList.purchaseDate.between(startDate, endDate).and(purchaseList.customerGender.eq(gender)) : purchaseList.customerGender.eq(gender))
                .groupBy(
                        purchaseList.customerGender,
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType
                )
                .orderBy(purchaseList.dishName.count().desc())
                .fetch();
    }

    @Override
    public List<StatisticsGradeResponse> findStatisticsByGrade(LocalDate startDate, LocalDate endDate, String grade) {
        return queryFactory
                .select(new QStatisticsGradeResponse(
                        purchaseList.customerGrade,
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType,
                        purchaseList.dishName.count().as("dishNameCount"),
                        purchaseList.roomType.count().as("roomTypeCount")
                ))
                .from(purchaseList)
                .where(startDate != null && endDate != null ? purchaseList.purchaseDate.between(startDate, endDate).and(purchaseList.customerGrade.eq(grade)) : purchaseList.customerGrade.eq(grade))
                .groupBy(
                        purchaseList.customerGrade,
                        purchaseList.consumeType,
                        purchaseList.dishName,
                        purchaseList.roomType
                )
                .orderBy(purchaseList.dishName.count().desc())
                .fetch();
    }
}