package com.team2final.minglecrm.persistence.repository.dining;


import com.team2final.minglecrm.entity.dining.DishReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishReservationDetailRepository extends JpaRepository<DishReservationDetail, Long> {
}

