package com.team2final.minglecrm.reservation.domain.hotel.repository;

import com.team2final.minglecrm.reservation.domain.hotel.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
}
