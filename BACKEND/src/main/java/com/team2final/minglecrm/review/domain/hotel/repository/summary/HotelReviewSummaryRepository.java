package com.team2final.minglecrm.review.domain.hotel.repository.summary;

import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelReviewSummaryRepository extends JpaRepository<HotelReviewSummary, Long> {
}
