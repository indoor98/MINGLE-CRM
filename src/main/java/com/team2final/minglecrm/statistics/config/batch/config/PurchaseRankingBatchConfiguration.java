package com.team2final.minglecrm.statistics.config.batch.config;

import com.team2final.minglecrm.entity.dining.DishReservation;
import com.team2final.minglecrm.entity.dining.DishReservationDetail;
import com.team2final.minglecrm.statistics.config.batch.JobCompletionNotificationListener;
import com.team2final.minglecrm.statistics.config.batch.RunIdIncrementer;
import com.team2final.minglecrm.statistics.entity.PurchaseDish;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class PurchaseRankingBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job purchaseItemJob(JobCompletionNotificationListener listener, Step purchaseItemStep, Step purchaseItemDeleteStep) {
        return new JobBuilder("purchaseItemJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(purchaseItemDeleteStep)
                .next(purchaseItemStep)
                .build();
    }

    @Bean
    public Step purchaseItemStep() {
        return new StepBuilder("purchaseItemStep", jobRepository)
                .<DishReservation, PurchaseDish>chunk(10, platformTransactionManager)
                .reader(purchaseItemReader())
                .processor(purchaseItemProcessor())
                .writer(purchaseItemWriter())
                .build();
    }

    @Bean
    public Step purchaseItemDeleteStep() {
        return new StepBuilder("purchaseItemDeleteStep", jobRepository)
                .tasklet(purchaseItemDeleteTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet purchaseItemDeleteTasklet() {
        return (contribution, chunkContext) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM PurchaseDish").executeUpdate();
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
    public JpaCursorItemReader<DishReservation> purchaseItemReader() {
        return new JpaCursorItemReaderBuilder<DishReservation>()
                .name("purchaseItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM DishReservation r")
                .build();
    }

    @Bean
    public ItemProcessor<DishReservation, PurchaseDish> purchaseItemProcessor() {
        return dishReservation -> {
            Map<String, List<DishReservationDetail>> groupedByDish = dishReservation.getDishReservationDetails().stream()
                    .collect(Collectors.groupingBy(detail -> detail.getDish().getName()));

            for (Map.Entry<String, List<DishReservationDetail>> entry : groupedByDish.entrySet()) {
                String dishName = entry.getKey();
                List<DishReservationDetail> details = entry.getValue();

                Integer totalQuantity = details.stream().mapToInt(DishReservationDetail::getQuantity).sum();
                Long totalAmount = details.stream().mapToLong(DishReservationDetail::getItemTotalPrice).sum();

                return PurchaseDish.builder()
                        .purchaseDate(dishReservation.getReservationDate().toLocalDate())
                        .name(dishName)
                        .quantity(totalQuantity)
                        .amount(totalAmount)
                        .build();
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<PurchaseDish> purchaseItemWriter() {
        return items -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            try {
                for (PurchaseDish item : items) {
                    entityManager.persist(item);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                entityManager.close();
            }
        };
    }
}