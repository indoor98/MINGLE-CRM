package com.team2final.minglecrm.config.batch;

import com.team2final.minglecrm.entity.hotel.RoomReservation;
import com.team2final.minglecrm.persistence.repository.hotel.RoomReservationRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ReservationBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final RoomReservationRepository roomReservationRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job monthlyReservationJob(JobCompletionNotificationListener listener, Step monthlyReservationStep) {
        return new JobBuilder("monthlyReservationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(monthlyReservationStep)
                .build();
    }

    @Bean
    public Step monthlyReservationStep(RepositoryItemReader<RoomReservation> monthlyReservationReader, ItemProcessor<RoomReservation, RoomReservation> monthlyReservationProcessor, ItemWriter<RoomReservation> monthlyReservationWriter) {
        return new StepBuilder("monthlyReservationStep", jobRepository)
                .<RoomReservation, RoomReservation>chunk(10, platformTransactionManager)
                .reader(monthlyReservationReader)
                .processor(monthlyReservationProcessor)
                .writer(monthlyReservationWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<RoomReservation> monthlyReservationReader() {
        Pageable pageable = PageRequest.of(0, 10);
        return new RepositoryItemReaderBuilder<RoomReservation>()
                .name("monthlyReservationReader")
                .repository(roomReservationRepository)
                .methodName("findReservationsByMonthAndYear")
                .arguments(1, 2024)
                .pageSize(10)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<RoomReservation, RoomReservation> monthlyReservationProcessor() {
        return roomReservation -> roomReservation;
    }

    @Bean
    public ItemWriter<RoomReservation> monthlyReservationWriter() {
        return new JpaItemWriterBuilder<RoomReservation>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

}
