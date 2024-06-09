package com.team2final.minglecrm.persistence.repository.hotel;


import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.controller.hotel.review.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.entity.hotel.HotelReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Long> {

    @EntityGraph(value = "HotelReview.withRoomReservationAndCustomer", type = EntityGraph.EntityGraphType.LOAD)
    Page<HotelReview> findAll(Pageable pageable);

}
