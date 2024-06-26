package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyReservationResponse {

    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Long reservationCount;

    @Builder
    public MonthlyReservationResponse(Long id, Integer reservationYear, Integer reservationMonth, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationCount = reservationCount;
    }
}
