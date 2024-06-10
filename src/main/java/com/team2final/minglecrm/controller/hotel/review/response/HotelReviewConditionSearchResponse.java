package com.team2final.minglecrm.controller.hotel.review.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.entity.hotel.type.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class HotelReviewConditionSearchResponse {

    private String customerName;
    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double convenienceRating;
    private Double locationRating;
    private String comment;
    private LocalDateTime createdTime;
    private RoomType roomType;
    private String hotel;


    @QueryProjection
    public HotelReviewConditionSearchResponse(
            String customerName,
            Double kindnessRating,
            Double cleanlinessRating,
            Double convenienceRating,
            Double locationRating,
            String comment,
            LocalDateTime createdTime,
            RoomType roomType,
            String hotel
    ) {
        this.customerName = customerName;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.convenienceRating = convenienceRating;
        this.locationRating = locationRating;
        this.comment = comment;
        this.createdTime = createdTime;
        this.roomType = roomType;
        this.hotel = hotel;
    }
}
