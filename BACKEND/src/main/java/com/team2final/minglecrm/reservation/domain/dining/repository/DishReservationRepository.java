package com.team2final.minglecrm.reservation.domain.dining.repository;

import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishReservationRepository extends JpaRepository<DishReservation, Long> {
    List<DishReservation> findByCustomerId(Long customerId);

}
