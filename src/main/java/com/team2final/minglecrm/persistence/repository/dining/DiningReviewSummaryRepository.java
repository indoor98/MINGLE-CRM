package com.team2final.minglecrm.persistence.repository.dining;

import com.team2final.minglecrm.entity.dining.DiningReviewSummary;
import com.team2final.minglecrm.entity.hotel.type.SummaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningReviewSummaryRepository extends JpaRepository<DiningReviewSummary, Long> {

    List<DiningReviewSummary> findDiningReviewSummariesBySummaryTypeOrderByStartDateDesc(SummaryType summaryType);
}
