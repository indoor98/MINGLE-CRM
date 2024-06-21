
package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyReservationResponse {

    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Integer reservationDay;
    private Long reservationCount;

    @Builder
    public DailyReservationResponse(Long id, Integer reservationYear, Integer reservationMonth, Integer reservationDay, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationDay = reservationDay;
        this.reservationCount = reservationCount;
    }
}
