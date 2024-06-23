package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomPurchaseListRepositoryImpl implements CustomPurchaseListRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StatisticsResponse> findStatistics(LocalDate startDate, LocalDate endDate,
                                                   boolean groupByDishName, boolean groupByRoomType,
                                                   boolean groupByCustomerGender, boolean groupByCustomerGrade) {
        StringBuilder jpql = new StringBuilder("SELECT new com.team2final.minglecrm.statistics.dto.response.purchase.StatisticsResponse(" +
                "p.id, p.purchaseDate, p.customerName, p.customerGrade, p.customerGender, p.consumeType, " +
                "p.dishName, p.roomType, COUNT(p.id)) " +
                "FROM PurchaseList p WHERE 1=1 ");

        if (startDate != null) {
            jpql.append("AND p.purchaseDate >= :startDate ");
        }
        if (endDate != null) {
            jpql.append("AND p.purchaseDate <= :endDate ");
        }

        List<String> groupByFields = new ArrayList<>();
        if (groupByDishName) {
            groupByFields.add("p.dishName");
        }
        if (groupByRoomType) {
            groupByFields.add("p.roomType");
        }
        if (groupByCustomerGender) {
            groupByFields.add("p.customerGender");
        }
        if (groupByCustomerGrade) {
            groupByFields.add("p.customerGrade");
        }

        if (!groupByFields.isEmpty()) {
            jpql.append("GROUP BY ").append(String.join(", ", groupByFields)).append(", p.id, p.purchaseDate, p.customerName, p.consumeType ");
        } else {
            jpql.append("GROUP BY p.id, p.purchaseDate, p.customerName, p.consumeType ");
        }

        Query query = entityManager.createQuery(jpql.toString(), StatisticsResponse.class);

        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        return query.getResultList();
    }
}
