package com.team2final.minglecrm.review.domain.dining.repository.review;

import com.team2final.minglecrm.config.TestQueryDslConfig;
import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import com.team2final.minglecrm.review.domain.dining.DiningReview;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestQueryDslConfig.class)
@TestPropertySource("classpath:application-test.yml")
class DiningReviewQueryRepositoryTest {

    @Autowired
    private DiningReviewQueryRepository diningReviewQueryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .name("testCustomer1")
                .build();
        entityManager.persist(customer);

        DishReservation dishReservation = DishReservation.builder()
                .customer(customer)
                .visitorCount(5)
                .restaurant("담소정")
                .build();
        entityManager.persist(dishReservation);

        DiningReview diningReview = DiningReview.builder()
                .atmosphereRating(5.0)
                .cleanlinessRating(1.0)
                .tasteRating(3.0)
                .kindnessRating(3.0)
                .dishReservation(dishReservation)
                .customer(customer)
                .review("아주 맛있습니다.")
                .createdTime(LocalDateTime.of(2024, 1, 1, 10, 25))
                .build();
        entityManager.persist(diningReview);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 조건 X : Paging O")
    void testSearchByExpression_withNoCondition_withPagination() {

        // Given
        DiningReviewConditionSearchRequest request = new DiningReviewConditionSearchRequest();
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 모든 조건 : Paging O")
    void testSearchByExpression_withAllCondition_withPagination() {

        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : Dining 지점 : Paging O")
    void testSearchByExpression_withDiningCondition_withPagination() {

        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 고객명 : Paging O")
    void testSearchByExpression_withCustomerName_withPagination() {

        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 날짜 : Paging O")
    void testSearchByExpression_withAllDate_withPagination() {

        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }


    @Test
    @DisplayName("Dining Review Count : 모든 조건")
    void testCountByExpression_withAllCondition() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        // When
        long count = diningReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dining Review Count : 식당")
    void testCountByExpression_withDining() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .build();

        // When
        long count = diningReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dining Review Count : 고객 명")
    void testCountByExpression_withCustomerName() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        // When
        Long count = diningReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dining Review Count : 날짜")
    void testCountByExpression_withDate() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        // When
        Long count = diningReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dining Review Count : 조건 X ")
    void testCountByExpression_withNoCondition() {
        // Given
        DiningReviewConditionSearchRequest request = new DiningReviewConditionSearchRequest();

        // When
        Long count = diningReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 식당 : Paging X")
    void testSearchByExpression_withDining_withoutPagination() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .build();

        // When
        List<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }


    @Test
    @DisplayName("Dining Review 다중 검색 : 고객 명 : Paging X")
    void testSearchByExpression_withCustomerName_withoutPagination() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        // When
        List<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 날짜 : Paging X")
    void testSearchByExpression_withAllDate_withoutPagination() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        // When
        List<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 모든 조건 : Paging X")
    void testSearchByExpression_withAllCondition_withoutPagination() {
        // Given
        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .restaurant("담소정")
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024,1,1,0,0))
                .endDate(LocalDateTime.of(2024,1, 2, 23,59,59))
                .build();

        // When
        List<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Dining Review 다중 검색 : 조건 X : Paging X")
    void testSearchByExpression_withNoCondition_withoutPagination() {
        DiningReviewConditionSearchRequest request = new DiningReviewConditionSearchRequest();

        List<DiningReviewConditionSearchResponse> result = diningReviewQueryRepository.searchByExpression(request);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }


}