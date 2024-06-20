package com.team2final.minglecrm.statistics.domain.repository;

import com.team2final.minglecrm.statistics.domain.ByYearReservationCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ByYearReservationCountRepository extends JpaRepository<ByYearReservationCount, Long> {
}
