package com.team2final.minglecrm.reservation.dto.dining.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateDiningReservationRequest {

    private String name;
    private Long price;
    private String memo;
    private LocalDateTime reservationDate;



}
