package com.team2final.minglecrm.reservation.domain.dining.repository;


import com.team2final.minglecrm.reservation.domain.dining.DishReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishReservationDetailRepository extends JpaRepository<DishReservationDetail, Long> {
}

