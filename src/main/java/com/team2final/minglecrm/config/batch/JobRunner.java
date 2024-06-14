package com.team2final.minglecrm.config.batch;

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

    public void runImportCustomerJob(Job importCustomerJob) throws Exception {
        runJob(importCustomerJob);
    }

    public void runVisitCustomerJob(Job visitCustomerJob) throws Exception {
        runJob(visitCustomerJob);
    }

    public void runMonthlyReservationJob(Job monthlyReservationJob) throws Exception {
        runJob(monthlyReservationJob);
    }
}
