package com.team2final.minglecrm.reservation.domain.hotel.repository;

import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    List<RoomReservation> findByCustomerId(Long customerId);

}
