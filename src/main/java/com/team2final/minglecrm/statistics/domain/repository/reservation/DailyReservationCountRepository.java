package com.team2final.minglecrm.statistics.domain.repository.reservation;

import com.team2final.minglecrm.statistics.domain.DailyReservationCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface DailyReservationCountRepository extends JpaRepository<DailyReservationCount, Long> {

    List<DailyReservationCount> findAllBy(Pageable pageable);
}
