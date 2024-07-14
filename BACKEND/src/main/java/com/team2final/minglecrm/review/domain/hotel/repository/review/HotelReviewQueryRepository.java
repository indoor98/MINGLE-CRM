package com.team2final.minglecrm.review.domain.hotel.repository.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.hotel.QHotelRoom;
import com.team2final.minglecrm.reservation.domain.hotel.QRoomReservation;
import com.team2final.minglecrm.reservation.domain.hotel.RoomType;
import com.team2final.minglecrm.review.domain.hotel.Hotel;
import com.team2final.minglecrm.review.domain.hotel.QHotelReview;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.QHotelReviewConditionSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable) {

        QHotelReview hotelReview = QHotelReview.hotelReview;
        QCustomer customer = QCustomer.customer;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;

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
                .where(
                        customerNameEq(condition.getCustomerName()),
                        hotelEq(condition.getHotel()),
                        roomTypeEq(condition.getRoomType()),
                        createdTimeBetween(condition.getStartDate(), condition.getEndDate())
                        )
                .offset(pageable.getOffset()) // offset
                .limit(pageable.getPageSize()) // limit
                .fetch();


        return new PageImpl<>(response, pageable, response.size());
    }

    public List<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition) {

        QHotelReview hotelReview = QHotelReview.hotelReview;
        QCustomer customer = QCustomer.customer;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;

        return queryFactory
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
                .where(
                        customerNameEq(condition.getCustomerName()),
                        hotelEq(condition.getHotel()),
                        roomTypeEq(condition.getRoomType()),
                        createdTimeBetween(condition.getStartDate(), condition.getEndDate())
                )
                .fetch();
    }

    public Long countByExpression(HotelReviewConditionSearchRequest condition) {
        QHotelReview hotelReview = QHotelReview.hotelReview;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;

        return queryFactory
                .select(hotelReview.count())
                .from(hotelReview)
                .join(hotelReview.roomReservation, roomReservation)
                .join(roomReservation.hotelRoom, hotelRoom)
                .where(
                        hotelEq(condition.getHotel()),
                        roomTypeEq(condition.getRoomType()),
                        createdTimeBetween(condition.getStartDate(), condition.getEndDate())
                        )
                .fetchOne();
    }

    public BooleanExpression customerNameEq(String customerName) {
        return customerName != null ? QCustomer.customer.name.eq(customerName) : null;
    }

    public BooleanExpression hotelEq(Hotel hotel) {
        return hotel != null ? QHotelRoom.hotelRoom.hotel.eq(hotel) : null;
    }

    public BooleanExpression roomTypeEq(RoomType roomType) {
        return roomType != null ? QHotelRoom.hotelRoom.roomType.eq(roomType) : null;
    }

    public BooleanExpression createdTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (startDate != null) && (endDate != null) ? QHotelReview.hotelReview.createdTime.between(startDate, endDate) : null;
    }


}
