package com.team2final.minglecrm.persistence.repository.dining;

import com.team2final.minglecrm.entity.dining.Dish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByDishReservationId(Long reservationId);
}
