package com.team2final.minglecrm.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.review.domain.hotel.repository.review.HotelReviewQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public HotelReviewQueryRepository hotelReviewQueryRepository() {
        return new HotelReviewQueryRepository(jpaQueryFactory());
    }

}
