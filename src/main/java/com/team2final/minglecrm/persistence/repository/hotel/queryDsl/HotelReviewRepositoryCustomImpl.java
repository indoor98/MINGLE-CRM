package com.team2final.minglecrm.persistence.repository.hotel.queryDsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.controller.hotel.review.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.controller.hotel.review.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.controller.hotel.review.response.QHotelReviewConditionSearchResponse;
import com.team2final.minglecrm.entity.customer.QCustomer;
import com.team2final.minglecrm.entity.hotel.QHotelReview;
import com.team2final.minglecrm.entity.hotel.QHotelRoom;
import com.team2final.minglecrm.entity.hotel.QRoomReservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelReviewRepositoryCustomImpl implements HotelReviewRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QHotelReview hotelReview = QHotelReview.hotelReview;
        QCustomer customer = QCustomer.customer;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getCustomerName() != null) {
            builder.and(customer.name.eq(condition.getCustomerName()));
        }
        if (condition.getHotel() != null) {
            builder.and(hotelRoom.hotel.eq(condition.getHotel())); // Hotel 정보 검색
        }
        if (condition.getRoomType() != null) {
            builder.and(hotelRoom.roomType.eq(condition.getRoomType()));
        }
        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(hotelReview.createdTime.between(condition.getStartDate(), condition.getEndDate()));
        }

        List<HotelReviewConditionSearchResponse> response =  queryFactory
                .select(new QHotelReviewConditionSearchResponse(
                        customer.name,
                        hotelReview.kindnessRating,
                        hotelReview.cleanlinessRating,
                        hotelReview.convenienceRating,
                        hotelReview.locationRating,
                        hotelReview.comment,
                        hotelReview.createdTime,
                        roomReservation.hotelRoom.roomType,
                        hotelRoom.hotel // Hotel 정보 추가
                ))
                .from(hotelReview)
                .join(hotelReview.customer, customer)
                .join(hotelReview.roomReservation, roomReservation)
                .join(roomReservation.hotelRoom, hotelRoom) // HotelRoom과 조인
                .where(builder)
                .offset(pageable.getOffset()) // offset
                .limit(pageable.getPageSize()) // limit
                .fetch();


        return new PageImpl<>(response, pageable, response.size());
    }
}
