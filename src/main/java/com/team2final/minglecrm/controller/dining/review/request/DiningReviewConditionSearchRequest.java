package com.team2final.minglecrm.controller.dining.review.request;

import com.team2final.minglecrm.entity.hotel.type.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DiningReviewConditionSearchRequest {

    private String customerName;
    private String restaurant;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
