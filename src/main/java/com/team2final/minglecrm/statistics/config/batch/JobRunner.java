package com.team2final.minglecrm.statistics.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRunner {

    private final JobLauncher jobLauncher;

    public void runJob(Job job) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    public void runImportFrequentCustomerJob(Job importFrequentCustomerJob) throws Exception {
        runJob(importFrequentCustomerJob);
    }

    public void runReservationRoomJob(Job reservationRoomJob) throws Exception {
        runJob(reservationRoomJob);
    }

    public void runReservationStatisticsJob(Job reservationStatisticsJob) throws Exception {
        runJob(reservationStatisticsJob);
    }

    public void runBirthdayReminderJob(Job birthdayReminderJob) throws Exception {
        runJob(birthdayReminderJob);
    }

    public void runPurchaseItemJob(Job purchaseItemJob) throws Exception {
        runJob(purchaseItemJob);
    }
}
