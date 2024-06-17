package com.team2final.minglecrm.log.domain.repository;

import com.team2final.minglecrm.log.domain.ViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewLogRepository extends JpaRepository<ViewLog, Long> {
}
