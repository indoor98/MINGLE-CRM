package com.team2final.minglecrm.statistics.persistance;

import com.team2final.minglecrm.statistics.entity.BirthdayReminderCustomers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayReminderCustomersRepository extends JpaRepository<BirthdayReminderCustomers, Long> {
}
