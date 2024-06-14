package com.team2final.minglecrm.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCommandLineRunner implements CommandLineRunner {

    private final JobRunner jobRunner;
    private final Job importCustomerJob;
    private final Job visitCustomerJob;
    private final Job monthlyReservationJob;

    @Override
    public void run(String ... args) throws Exception {
        jobRunner.runImportCustomerJob(importCustomerJob);
        jobRunner.runVisitCustomerJob(visitCustomerJob);
        jobRunner.runMonthlyReservationJob(monthlyReservationJob);
    }
}
