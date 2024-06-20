package com.team2final.minglecrm.statistics.config.batch.config;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.statistics.config.batch.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.entity.FrequentCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CustomerBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job importFrequentCustomerJob(JobCompletionNotificationListener listener, Step importFrequentCustomerStep, Step frequentCustomerDeleteStep) {
        return new JobBuilder("importFrequentCustomerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(frequentCustomerDeleteStep)
                .next(importFrequentCustomerStep)
                .build();
    }

    @Bean
    public Step importFrequentCustomerStep() {
        return new StepBuilder("importFrequentCustomerStep", jobRepository)
                .<Customer, FrequentCustomer>chunk(10, platformTransactionManager)
                .reader(importFrequentCustomerReader())
                .processor(importFrequentCustomerProcessor())
                .writer(importFrequentCustomerWriter())
                .build();
    }

    @Bean
    public Step frequentCustomerDeleteStep() {
        return new StepBuilder("frequentCustomerDeleteStep", jobRepository)
                .tasklet(frequentCustomerDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet frequentCustomerDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM FrequentCustomer").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                entityManager.close();
            }
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public JpaCursorItemReader<Customer> importFrequentCustomerReader() {
        return new JpaCursorItemReaderBuilder<Customer>()
                .name("importFrequentCustomerReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM Customer c WHERE c.visitCnt >= 2 AND c.isDeleted = false")
                .build();
    }

    @Bean
    public ItemProcessor<Customer, FrequentCustomer> importFrequentCustomerProcessor() {
        return customer -> FrequentCustomer.builder()
                .name(customer.getName())
                .phone(customer.getPhone())
                .employeeName(customer.getEmployee().getName()) // Assuming Employee has a getName() method
                .createdDate(customer.getCreatedDate())
                .memo(customer.getMemo())
                .grade(customer.getGrade())
                .address(customer.getAddress())
                .gender(customer.getGender())
                .visitCnt(customer.getVisitCnt())
                .birth(customer.getBirth())
                .build();
    }

    @Bean
    public ItemWriter<FrequentCustomer> importFrequentCustomerWriter() {
        return new JpaItemWriterBuilder<FrequentCustomer>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }


}

