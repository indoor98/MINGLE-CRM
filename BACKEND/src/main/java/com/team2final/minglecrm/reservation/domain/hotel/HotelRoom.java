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
import org.springframework.transaction.annotation.Transactional;

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

    /* 테스트를 위한 정적 팩터리 메서드 */
    public static HotelRoom create(Hotel hotel, RoomType roomType) {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.hotel = hotel;
        hotelRoom.roomType = roomType;
        return hotelRoom;
    }
}
