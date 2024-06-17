package com.team2final.minglecrm.statistics.config.support;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCommandLineRunner implements CommandLineRunner {

    private final JobRunner jobRunner;
    private final Job importFrequentCustomerJob;
    private final Job reservationRoomJob;
    private final Job reservationStatisticsJob;
    private final Job birthdayReminderJob;

    @Override
    public void run(String ... args) throws Exception {
        jobRunner.runImportFrequentCustomerJob(importFrequentCustomerJob);
        jobRunner.runReservationRoomJob(reservationRoomJob);
//        jobRunner.runReservationStatisticsJob(reservationStatisticsJob);
        jobRunner.runBirthdayReminderJob(birthdayReminderJob);
    }

}
