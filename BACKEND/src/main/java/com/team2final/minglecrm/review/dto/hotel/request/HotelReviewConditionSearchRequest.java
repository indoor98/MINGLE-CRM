package com.team2final.minglecrm.review.dto.hotel.request;


import com.team2final.minglecrm.reservation.domain.hotel.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelReviewConditionSearchRequest {

    private String customerName;
    private String hotel;
    private RoomType roomType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
