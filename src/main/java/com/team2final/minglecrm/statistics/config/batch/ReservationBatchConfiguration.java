package com.team2final.minglecrm.statistics.config.batch;

import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import com.team2final.minglecrm.statistics.config.support.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.config.support.RunIdIncrementer;
import com.team2final.minglecrm.statistics.domain.StatisticsRoomReservation;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ReservationBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job reservationRoomJob(JobCompletionNotificationListener listener, Step reservationRoomStep) {
        return new JobBuilder("ReservationRoomJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(reservationRoomStep)
                .build();
    }

    @Bean
    public Step reservationRoomStep() {
        return new StepBuilder("reservationRoomStep", jobRepository)
                .<RoomReservation, StatisticsRoomReservation>chunk(10, platformTransactionManager)
                .reader(reservationRoomReader())
                .processor(reservationRoomProcessor())
                .writer(reservationRoomWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<RoomReservation> reservationRoomReader() {
        return new JpaCursorItemReaderBuilder<RoomReservation>()
                .name("reservationRoomReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM RoomReservation r WHERE r.payment.isRefunded = false")
                .build();
    }

    @Bean
    public ItemProcessor<RoomReservation, StatisticsRoomReservation> reservationRoomProcessor() {
        return roomReservation -> StatisticsRoomReservation.builder()
                .hotelRoomId(roomReservation.getHotelRoom().getId())
                .roomType(roomReservation.getHotelRoom().getRoomType().name())
                .startDate(roomReservation.getStartDate())
                .endDate(roomReservation.getEndDate())
                .build();
    }

    @Bean
    public ItemWriter<StatisticsRoomReservation> reservationRoomWriter() {
        return new JpaItemWriterBuilder<StatisticsRoomReservation>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
