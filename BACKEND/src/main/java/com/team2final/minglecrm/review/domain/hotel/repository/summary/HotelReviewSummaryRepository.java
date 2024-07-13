package com.team2final.minglecrm.review.domain.hotel.repository.summary;

import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotelReviewSummaryRepository extends JpaRepository<HotelReviewSummary, Long> {

    List<HotelReviewSummary> findHotelReviewSummariesBySummaryTypeAndStartDateAndEndDateAndHotel(SummaryType summaryType, LocalDateTime startDate, LocalDateTime endDate, String hotel);
}
