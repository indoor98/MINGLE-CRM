package com.team2final.minglecrm.statistics.domain.repository.purchase;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.statistics.domain.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.team2final.minglecrm.statistics.domain.QPurchaseList.purchaseList;

@Repository
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Long>, PurchaseListRepositoryCustom {
}