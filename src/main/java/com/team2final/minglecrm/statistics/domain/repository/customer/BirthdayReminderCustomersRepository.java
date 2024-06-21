package com.team2final.minglecrm.statistics.domain.repository.customer;

import com.team2final.minglecrm.statistics.domain.BirthdayReminderCustomers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayReminderCustomersRepository extends JpaRepository<BirthdayReminderCustomers, Long> {
}
