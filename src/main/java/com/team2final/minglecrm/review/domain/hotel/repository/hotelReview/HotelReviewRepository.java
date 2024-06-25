package com.team2final.minglecrm.review.domain.hotel.repository.hotelReview;


import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Long>, HotelReviewQueryDslRepository {

    @EntityGraph(value = "HotelReview.withRoomReservationAndCustomer", type = EntityGraph.EntityGraphType.LOAD)
    Page<HotelReview> findAll(Pageable pageable);

    List<HotelReview> findHotelReviewByCreatedTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    Long countHotelReviewByCreatedTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
