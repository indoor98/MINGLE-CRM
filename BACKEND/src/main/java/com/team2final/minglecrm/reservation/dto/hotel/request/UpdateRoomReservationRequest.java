package com.team2final.minglecrm.reservation.dto.hotel.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateRoomReservationRequest {

    private String name;
    private Long price;
    private String memo;
    private LocalDateTime reservationDate;

}
