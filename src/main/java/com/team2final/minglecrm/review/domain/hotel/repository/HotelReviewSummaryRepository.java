package com.team2final.minglecrm.review.domain.hotel.repository;

import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewSummaryRepository extends JpaRepository<HotelReviewSummary, Long> {

    List<HotelReviewSummary> findHotelReviewSummariesBySummaryTypeOrderByStartDateDesc(SummaryType summaryType);
}
