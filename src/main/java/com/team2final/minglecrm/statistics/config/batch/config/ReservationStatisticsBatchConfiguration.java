package com.team2final.minglecrm.statistics.config.batch.config;

import com.team2final.minglecrm.entity.hotel.RoomReservation;
import com.team2final.minglecrm.statistics.config.batch.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.config.batch.RunIdIncrementer;
import com.team2final.minglecrm.statistics.entity.ByYearReservationCount;
import com.team2final.minglecrm.statistics.entity.DailyReservationCount;
import com.team2final.minglecrm.statistics.entity.MonthlyReservationCount;
import com.team2final.minglecrm.statistics.entity.WeeklyReservationCount;
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
import java.time.temporal.IsoFields;

@Configuration
@RequiredArgsConstructor
public class ReservationStatisticsBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job reservationStatisticsJob(JobCompletionNotificationListener listener) {
        return new JobBuilder("ReservationStatisticsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(byYearReservationDeleteStep())
                .next(byYearReservationStep())
                .next(monthlyReservationDeleteStep())
                .next(monthlyReservationStep())
                .next(weeklyReservationDeleteStep())
                .next(weeklyReservationStep())
                .next(dailyReservationDeleteStep())
                .next(dailyReservationStep())
                .build();
    }

    @Bean
    public Step byYearReservationDeleteStep() {
        return new StepBuilder("byYearReservationDeleteStep", jobRepository)
                .tasklet(byYearReservationDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step monthlyReservationDeleteStep() {
        return new StepBuilder("monthlyReservationDeleteStep", jobRepository)
                .tasklet(monthlyReservationDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step weeklyReservationDeleteStep() {
        return new StepBuilder("weeklyReservationDeleteStep", jobRepository)
                .tasklet(dailyReservationDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step dailyReservationDeleteStep() {
        return new StepBuilder("dailyReservationDeleteStep", jobRepository)
                .tasklet(dailyReservationDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step byYearReservationStep() {
        return new StepBuilder("byYearReservationStep", jobRepository)
                .<Object[], ByYearReservationCount>chunk(10, platformTransactionManager)
                .reader(byYearReservationReader())
                .processor(byYearReservationCountItemProcessor())
                .writer(byYearReservationCountItemWriter())
                .build();
    }

    @Bean
    public Step monthlyReservationStep() {
        return new StepBuilder("monthlyReservationStep", jobRepository)
                .<Object[], MonthlyReservationCount>chunk(10, platformTransactionManager)
                .reader(monthlyReservationReader())
                .processor(monthlyReservationCountItemProcessor())
                .writer(monthlyReservationCountItemWriter())
                .build();
    }

    @Bean
    public Step weeklyReservationStep() {
        return new StepBuilder("weeklyReservationStep", jobRepository)
                .<Object[], WeeklyReservationCount>chunk(10, platformTransactionManager)
                .reader(weeklyReservationReader())
                .processor(weeklyReservationCountItemProcessor())
                .writer(weeklyReservationCountItemWriter())
                .build();
    }

    @Bean
    public Step dailyReservationStep() {
        return new StepBuilder("dailyReservationStep", jobRepository)
                .<Object[], DailyReservationCount>chunk(10, platformTransactionManager)
                .reader(dailyReservationReader())
                .processor(dailyReservationCountItemProcessor())
                .writer(dailyReservationCountItemWriter())
                .build();
    }

    @Bean
    public Tasklet byYearReservationDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM ByYearReservationCount").executeUpdate();
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
    public Tasklet monthlyReservationDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM MonthlyReservationCount").executeUpdate();
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
    public Tasklet weeklyReservationDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM WeeklyReservationCount").executeUpdate();
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
    public Tasklet dailyReservationDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM DailyReservationCount").executeUpdate();
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

//    @Bean
//    public JpaCursorItemReader<RoomReservation> byYearReservationReader() {
//        return new JpaCursorItemReaderBuilder<RoomReservation>()
//                .name("byYearReservationReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT r FROM RoomReservation r")
//                .build();
//    }
//
//    @Bean
//    public JpaCursorItemReader<RoomReservation> monthlyReservationReader() {
//        return new JpaCursorItemReaderBuilder<RoomReservation>()
//                .name("monthlyReservationReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT r FROM RoomReservation r")
//                .build();
//    }
//
//    @Bean
//    public JpaCursorItemReader<RoomReservation> weeklyReservationReader() {
//        return new JpaCursorItemReaderBuilder<RoomReservation>()
//                .name("weeklyReservationReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT r FROM RoomReservation r")
//                .build();
//    }
//
//    @Bean
//    public JpaCursorItemReader<RoomReservation> dailyReservationReader() {
//        return new JpaCursorItemReaderBuilder<RoomReservation>()
//                .name("dailyReservationReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT r FROM RoomReservation r")
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<RoomReservation, ByYearReservationCount> byYearReservationCountItemProcessor() {
//        return roomReservation -> ByYearReservationCount.builder()
//                .reservationYear(roomReservation.getStartDate().getYear())
//                .reservationCount(1L)
//                .build();
//    }
//
//
//    @Bean
//    public ItemProcessor<RoomReservation, MonthlyReservationCount> monthlyReservationCountItemProcessor() {
//        return roomReservation -> {
//            LocalDate reservationDate = roomReservation.getStartDate();
//            Integer year = reservationDate.getYear();
//            Integer mont = reservationDate.getMonthValue();
//
//            return MonthlyReservationCount.builder()
//                    .reservationYear(year)
//                    .reservationMonth(mont)
//                    .reservationCount(1L)
//                    .build();
//        };
//    }
//
//    @Bean
//    public ItemProcessor<RoomReservation, WeeklyReservationCount> weeklyReservationCountItemProcessor() {
//        return roomReservation -> {
//            LocalDate reservationDate = roomReservation.getStartDate();
//            Integer year = reservationDate.getYear();
//            Integer week = reservationDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
//
//            return WeeklyReservationCount.builder()
//                    .reservationYear(year)
//                    .reservationWeek(week)
//                    .reservationCount(1L)
//                    .build();
//        };
//    }
//
//    @Bean
//    public ItemProcessor<RoomReservation, DailyReservationCount> dailyReservationCountItemProcessor() {
//        return roomReservation -> {
//            LocalDate reservationDate = roomReservation.getStartDate();
//            Integer year = reservationDate.getYear();
//            Integer month = reservationDate.getMonthValue();
//            Integer day = reservationDate.getDayOfMonth();
//
//            return DailyReservationCount.builder()
//                    .reservationYear(year)
//                    .reservationMonth(month)
//                    .reservationDay(day)
//                    .reservationCount(1L)
//                    .build();
//        };
//    }

    @Bean
    public JpaCursorItemReader<Object[]> byYearReservationReader() {
        return new JpaCursorItemReaderBuilder<Object[]>()
                .name("byYearReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT YEAR(r.startDate), COUNT(r) FROM RoomReservation r GROUP BY YEAR(r.startDate)")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Object[]> monthlyReservationReader() {
        return new JpaCursorItemReaderBuilder<Object[]>()
                .name("monthlyReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT YEAR(r.startDate), MONTH(r.startDate), COUNT(r) FROM RoomReservation r GROUP BY YEAR(r.startDate), MONTH(r.startDate)")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Object[]> weeklyReservationReader() {
        return new JpaCursorItemReaderBuilder<Object[]>()
                .name("weeklyReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT YEAR(r.startDate), FUNCTION('week', r.startDate), COUNT(r) FROM RoomReservation r GROUP BY YEAR(r.startDate), FUNCTION('week', r.startDate)")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Object[]> dailyReservationReader() {
        return new JpaCursorItemReaderBuilder<Object[]>()
                .name("dailyReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT YEAR(r.startDate), MONTH(r.startDate), DAY(r.startDate), COUNT(r) FROM RoomReservation r GROUP BY YEAR(r.startDate), MONTH(r.startDate), DAY(r.startDate)")
                .build();
    }

    @Bean
    public ItemProcessor<Object[], ByYearReservationCount> byYearReservationCountItemProcessor() {
        return result -> ByYearReservationCount.builder()
                .reservationYear((Integer) result[0])
                .reservationCount((Long) result[1])
                .build();
    }

    @Bean
    public ItemProcessor<Object[], MonthlyReservationCount> monthlyReservationCountItemProcessor() {
        return result -> MonthlyReservationCount.builder()
                .reservationYear((Integer) result[0])
                .reservationMonth((Integer) result[1])
                .reservationCount((Long) result[2])
                .build();
    }

    @Bean
    public ItemProcessor<Object[], WeeklyReservationCount> weeklyReservationCountItemProcessor() {
        return result -> WeeklyReservationCount.builder()
                .reservationYear((Integer) result[0])
                .reservationWeek((Integer) result[1])
                .reservationCount((Long) result[2])
                .build();
    }

    @Bean
    public ItemProcessor<Object[], DailyReservationCount> dailyReservationCountItemProcessor() {
        return result -> DailyReservationCount.builder()
                .reservationYear((Integer) result[0])
                .reservationMonth((Integer) result[1])
                .reservationDay((Integer) result[2])
                .reservationCount((Long) result[3])
                .build();
    }


    @Bean
    public ItemWriter<ByYearReservationCount> byYearReservationCountItemWriter() {
        return new JpaItemWriterBuilder<ByYearReservationCount>() {}
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemWriter<MonthlyReservationCount> monthlyReservationCountItemWriter() {
        return new JpaItemWriterBuilder<MonthlyReservationCount>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemWriter<WeeklyReservationCount> weeklyReservationCountItemWriter() {
        return new JpaItemWriterBuilder<WeeklyReservationCount>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemWriter<DailyReservationCount> dailyReservationCountItemWriter() {
        return new JpaItemWriterBuilder<DailyReservationCount>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
