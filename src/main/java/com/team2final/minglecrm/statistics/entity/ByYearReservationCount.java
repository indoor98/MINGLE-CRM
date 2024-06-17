package com.team2final.minglecrm.statistics.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ByYearReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Long reservationCount;

    @Builder
    public ByYearReservationCount(Integer reservationYear, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationCount = reservationCount;
    }
}
