package com.team2final.minglecrm.statistics.config.batch;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.statistics.config.support.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.config.support.RunIdIncrementer;
import com.team2final.minglecrm.statistics.domain.BirthdayReminderCustomers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BirthdayReminderBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job birthdayReminderJob(JobCompletionNotificationListener listener, Step birthdayReminderStep, Step birthdayReminderDeleteStep) {
        return new JobBuilder("birthdayReminderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(birthdayReminderDeleteStep)
                .next(birthdayReminderStep)
                .build();
    }

    @Bean
    public Step birthdayReminderStep() {
        return new StepBuilder("birthdayReminderStep", jobRepository)
                .<Customer, BirthdayReminderCustomers>chunk(10, platformTransactionManager)
                .reader(birthdayReminderReader())
                .processor(birthdayReminderProcessor())
                .writer(birthdayReminderWriter())
                .build();
    }

    @Bean
    public Step birthdayReminderDeleteStep() {
        return new StepBuilder("birthdayReminderDeleteStep", jobRepository)
                .tasklet(birthdayReminderDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet birthdayReminderDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM BirthdayReminderCustomers").executeUpdate();
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
    public JpaCursorItemReader<Customer> birthdayReminderReader() {
        LocalDate oneWeekLater = LocalDate.now().plusDays(7);
        int month = oneWeekLater.getMonthValue();
        int day = oneWeekLater.getDayOfMonth();

        String jpqlQuery = "SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.birth) = :month AND FUNCTION('DAY', c.birth) = :day";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("month", month);
        parameters.put("day", day);

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("birthdayReminderReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(jpqlQuery)
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public ItemProcessor<Customer, BirthdayReminderCustomers> birthdayReminderProcessor() {
        return customer -> BirthdayReminderCustomers.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .birth(customer.getBirth())
                .build();
    }

    @Bean
    public ItemWriter<BirthdayReminderCustomers> birthdayReminderWriter() {
        return new JpaItemWriterBuilder<BirthdayReminderCustomers>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
