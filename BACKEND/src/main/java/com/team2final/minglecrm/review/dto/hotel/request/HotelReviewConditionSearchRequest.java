package com.team2final.minglecrm.review.dto.hotel.request;


import com.team2final.minglecrm.reservation.domain.hotel.RoomType;
import com.team2final.minglecrm.review.domain.hotel.Hotel;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HotelReviewConditionSearchRequest {

    private String customerName;
    private Hotel hotel;
    private RoomType roomType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public HotelReviewConditionSearchRequest(
            String customerName,
            Hotel hotel,
            RoomType roomType,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        this.customerName = customerName;
        this.hotel = hotel;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;

    }
}
