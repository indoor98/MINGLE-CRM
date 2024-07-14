package com.team2final.minglecrm.reservation.domain.hotel;

import com.team2final.minglecrm.review.domain.hotel.Hotel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Hotel hotel;

    private String address;

    private Integer roomNumber;

    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    private String roomState;
}
