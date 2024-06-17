package com.team2final.minglecrm.statistics.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Integer reservationDay;
    private Long reservationCount;

    @Builder
    public DailyReservationCount(Integer reservationYear, Integer reservationMonth, Integer reservationDay, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationDay = reservationDay;
        this.reservationCount = reservationCount;
    }
}
