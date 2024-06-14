//package com.team2final.minglecrm.config.batch;
//
//import com.team2final.minglecrm.entity.batch.BatchCustomer;
//import com.team2final.minglecrm.entity.customer.Customer;
//import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.RepositoryItemReader;
//import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
//import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.time.LocalDate;
//import java.util.Collections;
//
//@Configuration
//@RequiredArgsConstructor
//public class CustomerBatchConfiguration {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformTransactionManager;
//    private final CustomerRepository customerRepository;
//    private final EntityManagerFactory entityManagerFactory;
//    private final BatchCustomerRepository batchCustomerRepository;
//
//    @Bean
//    public Job importCustomerJob(JobCompletionNotificationListener listener, Step importCustomerStep) {
//        return new JobBuilder("importCustomerJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .start(importCustomerStep)
//                .build();
//    }
//
//    @Bean
//    public Step importCustomerStep(RepositoryItemReader<Customer> customerItemReader, ItemProcessor<Customer, Customer> customerItemProcessor, ItemWriter<Customer> customerItemWriter) {
//        return new StepBuilder("importCustomerStep", jobRepository)
//                .<Customer, Customer>chunk(10, platformTransactionManager)
//                .reader(customerItemReader)
//                .processor(customerItemProcessor)
//                .writer(customerItemWriter)
//                .build();
//    }
//
//    @Bean
//    public RepositoryItemReader<Customer> customerItemReader() {
//        Pageable pageable = PageRequest.of(0, 10);
//        return new RepositoryItemReaderBuilder<Customer>()
//                .name("customerReader")
//                .repository(customerRepository)
//                .methodName("findByCreatedDateBetween")
//                .arguments(LocalDate.MIN, LocalDate.MAX)
//                .pageSize(10)
//                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Customer, BatchCustomer> customerItemProcessor() {
//        return customer -> new BatchCustomer(
//                customer.getId(),
//                customer.getName(),
//                customer.getPhone(),
//                customer.getEmployee().getName(),
//                customer.getCreatedDate(),
//                customer.getMemo(),
//                customer.getGrade(),
//                customer.getAddress(),
//                customer.getGender(),
//                customer.getVisitCnt(),
//                customer.getBirth()
//        );
//    }
//
//    @Bean
//    public ItemWriter<BatchCustomer> customerItemWriter() {
//        return items -> batchCustomerRepository.saveAll(items);
//    }
//
//    @Bean
//    public Job visitCustomerJob(JobCompletionNotificationListener listener, Step visitCustomerStep) {
//        return new JobBuilder("visitCustomerJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .start(visitCustomerStep)
//                .build();
//    }
//
//    @Bean
//    public Step visitCustomerStep(RepositoryItemReader<Customer> visitCustomerItemReader, ItemProcessor<Customer, Customer> visitCustomerProcessor, ItemWriter<Customer> visitCustomerWriter) {
//        return new StepBuilder("visitCustomerStep", jobRepository)
//                .<Customer, Customer>chunk(10, platformTransactionManager)
//                .reader(visitCustomerItemReader)
//                .processor(visitCustomerProcessor)
//                .writer(visitCustomerWriter)
//                .build();
//    }
//
//    @Bean
//    public RepositoryItemReader<Customer> visitCustomerItemReader() {
//        Pageable pageable = PageRequest.of(0, 10);
//        return new RepositoryItemReaderBuilder<Customer>()
//                .name("visitCustomerItemReader")
//                .repository(customerRepository)
//                .methodName("findCustomersByReservationDateBetween")
//                .arguments(LocalDate.MIN, LocalDate.MAX)
//                .pageSize(10)
//                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Customer, Customer> visitCustomerProcessor() {
//        return customer -> customer;
//    }
//
//    @Bean
//    public ItemWriter<Customer> visitCustomerWriter() {
//        return new JpaItemWriterBuilder<Customer>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }
//
//}
//
