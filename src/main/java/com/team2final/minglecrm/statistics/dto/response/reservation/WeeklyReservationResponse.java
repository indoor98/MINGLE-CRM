
package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeeklyReservationResponse {

    private Long id;
    private Integer reservationYear;
    private Integer reservationWeek;
    private Long reservationCount;

    @Builder
    public WeeklyReservationResponse(Long id, Integer reservationYear, Integer reservationWeek, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationWeek = reservationWeek;
        this.reservationCount = reservationCount;
    }
}
