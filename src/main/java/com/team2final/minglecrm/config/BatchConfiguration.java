package com.team2final.minglecrm.config;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;

import static com.team2final.minglecrm.entity.customer.QCustomer.customer;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job importUserJob(JobExecutionListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1(ItemReader<Customer> reader,
                      ItemProcessor<Customer, Customer> processor,
                      ItemWriter<Customer> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Customer, Customer>chunk(10, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public RepositoryItemReader<Customer> reader(CustomerRepository customerRepository) {
        return new RepositoryItemReaderBuilder<Customer>()
                .repository(customerRepository)
                .methodName("findByCreatedDate")
                .arguments(LocalDate.now().minusDays(30), LocalDate.now())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("customerItemReader")
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        return customer -> customer;
    }

    @Bean
    public ItemWriter<Customer> writer() {
        return list -> {
            System.out.println("신규 유입자 수 : " + list.size());
            list.forEach(customer -> System.out.println(customer.getName()));
        };
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListenerSupport() {

        };
    }

}
