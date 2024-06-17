package com.team2final.minglecrm.statistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class StatisticsRoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hotelRoomId;
    private String roomType;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public StatisticsRoomReservation(Long id, Long hotelRoomId, String roomType, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.hotelRoomId = hotelRoomId;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
