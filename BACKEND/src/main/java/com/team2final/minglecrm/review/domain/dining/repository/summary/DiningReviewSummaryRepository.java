package com.team2final.minglecrm.review.domain.dining.repository.summary;

import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.SummaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiningReviewSummaryRepository extends JpaRepository<DiningReviewSummary, Long> {

    List<DiningReviewSummary> findDiningReviewSummariesBySummaryTypeOrderByStartDateDesc(SummaryType summaryType);
    List<DiningReviewSummary> findDiningReviewSummariesBySummaryTypeAndStartDateAndEndDateAndRestaurant(SummaryType summaryType, LocalDateTime startDate, LocalDateTime endDate, String restaurant);
}
