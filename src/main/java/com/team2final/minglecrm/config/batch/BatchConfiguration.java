package com.team2final.minglecrm.config.batch;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final CustomerRepository customerRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job importCustomerJob(JobCompletionNotificationListener listener, Step importCustomerStep) {
        return new JobBuilder("importCustomerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(importCustomerStep)
                .build();
    }

    @Bean
    public Step importCustomerStep(RepositoryItemReader<Customer> customerItemReader, ItemProcessor<Customer, Customer> customerItemProcessor, ItemWriter<Customer> customerItemWriter) {
        return new StepBuilder("importCustomerStep", jobRepository)
                .<Customer, Customer>chunk(10, platformTransactionManager)
                .reader(customerItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<Customer> customerItemReader() {
        return new RepositoryItemReaderBuilder<Customer>()
                .name("customerReader")
                .repository(customerRepository)
                .methodName("findByCreatedDateBetween")
                .arguments(LocalDate.now().minusMonths(1), LocalDate.now())
                .pageSize(10)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> customerItemProcessor() {
        return customer -> customer;
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return new JpaItemWriterBuilder<Customer>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Job visitCustomerJob(JobCompletionNotificationListener listener, Step visitCustomerStep) {
        return new JobBuilder("visitCustomerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(visitCustomerStep)
                .build();
    }

    @Bean
    public Step visitCustomerStep(RepositoryItemReader<Customer> visitCustomerItemReader, ItemProcessor<Customer, Customer> visitCustomerProcessor, ItemWriter<Customer> visitCustomerWriter) {
        return new StepBuilder("visitCustomerStep", jobRepository)
                .<Customer, Customer>chunk(10, platformTransactionManager)
                .reader(visitCustomerItemReader)
                .processor(visitCustomerProcessor)
                .writer(visitCustomerWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<Customer> visitCustomerItemReader() {
        return new RepositoryItemReaderBuilder<Customer>()
                .name("visitCustomerItemReader")
                .repository(customerRepository)
                .methodName("findCustomersByReservationDateBetween")
                .arguments(0, LocalDate.now().minusMonths(1), LocalDate.now(), PageRequest.of(0, 10))
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> visitCustomerProcessor() {
        return customer -> customer;
    }

    @Bean
    public ItemWriter<Customer> visitCustomerWriter() {
        return new JpaItemWriterBuilder<Customer>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}

