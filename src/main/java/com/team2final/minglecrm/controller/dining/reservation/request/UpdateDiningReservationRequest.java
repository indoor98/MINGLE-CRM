package com.team2final.minglecrm.controller.dining.reservation.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateDiningReservationRequest {

    private String name;
    private Long price;
    private String memo;
    private LocalDateTime reservationDate;



}
