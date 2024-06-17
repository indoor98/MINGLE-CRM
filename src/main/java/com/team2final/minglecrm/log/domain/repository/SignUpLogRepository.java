package com.team2final.minglecrm.log.domain.repository;

import com.team2final.minglecrm.log.domain.SignUpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpLogRepository extends JpaRepository<SignUpLog, Repository> {

}
