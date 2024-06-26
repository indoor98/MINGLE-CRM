package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ByYearReservationResponse {

    private Long id;
    private Integer reservationYear;
    private Long reservationCount;

    @Builder
    public ByYearReservationResponse(Long id, Integer reservationYear, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationCount = reservationCount;
    }
}
