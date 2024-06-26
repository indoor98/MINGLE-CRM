package com.team2final.minglecrm.statistics.domain.repository.reservation;

import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query("SELECT r FROM RoomReservation r WHERE FUNCTION('MONTH', r.startDate) = :month AND FUNCTION('YEAR', r.startDate) = :year")
    Page<RoomReservation> findReservationsByMonthAndYear(Integer year, Integer month, Pageable pageable);

}
