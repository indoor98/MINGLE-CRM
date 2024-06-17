package com.team2final.minglecrm.persistence.repository.hotel.queryDsl;

import com.team2final.minglecrm.entity.hotel.HotelReviewSummary;
import com.team2final.minglecrm.entity.hotel.type.SummaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewSummaryRepository extends JpaRepository<HotelReviewSummary, Long> {

    List<HotelReviewSummary> findHotelReviewSummariesBySummaryTypeOrderByStartDateDesc(SummaryType summaryType);
}
