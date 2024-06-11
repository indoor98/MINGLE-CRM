package com.team2final.minglecrm.controller.hotel.review.request;


import com.team2final.minglecrm.entity.hotel.type.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HotelReviewConditionSearchRequest {

    private String customerName;
    private String hotel;
    private RoomType roomType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
