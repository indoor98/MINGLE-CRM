package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StatisticsRoomReservationResponse {

    private Long id;
    private Long hotelRoomId;
    private Long customerId;
    private LocalDateTime reservationDate;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public StatisticsRoomReservationResponse(Long id, Long hotelRoomId, Long customerId, LocalDateTime reservationDate, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.hotelRoomId = hotelRoomId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
