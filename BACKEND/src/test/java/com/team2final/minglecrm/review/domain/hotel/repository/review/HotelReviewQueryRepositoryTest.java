package com.team2final.minglecrm.review.domain.hotel.repository.review;

import com.team2final.minglecrm.config.TestQueryDslConfig;
import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.reservation.domain.hotel.HotelRoom;
import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import com.team2final.minglecrm.reservation.domain.hotel.RoomType;
import com.team2final.minglecrm.review.domain.hotel.Hotel;
import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Import(TestQueryDslConfig.class)
class HotelReviewQueryRepositoryTest {

    @Autowired
    private HotelReviewQueryRepository hotelReviewQueryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        HotelRoom hotelRoom = HotelRoom.create(Hotel.SEOUL, RoomType.SUPERIOR);
        entityManager.persist(hotelRoom);

        Customer customer = Customer.builder()
                .name("testCustomer1")
                .build();
        entityManager.persist(customer);

        RoomReservation roomReservation = RoomReservation.create(customer, hotelRoom);
        entityManager.persist(roomReservation);

        HotelReview hotelReview = HotelReview.builder()
                .roomReservation(roomReservation)
                .cleanlinessRating(5.0)
                .locationRating(4.0)
                .cleanlinessRating(1.0)
                .convenienceRating(2.0)
                .comment("아주 좋은 호텔입니다.")
                .customer(customer)
                .createdTime(LocalDateTime.of(2024, 1, 1, 10, 35))
                .build();

        entityManager.persist(hotelReview);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 조건 X : Paging O")
    void testSearchByExpression_withNoCondition_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest();
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 모든 조건 : Paging O")
    void testSearchByExpression_withAllCondition_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .roomType(RoomType.SUPERIOR)
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCustomerName()).isEqualTo("testCustomer1");
        assertThat(result.getContent().get(0).getCreatedTime()).isBetween(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2024, 1, 2, 23, 59, 59));
        assertThat(result.getContent().get(0).getHotel()).isEqualTo(Hotel.SEOUL);
        assertThat(result.getContent().get(0).getRoomType()).isEqualTo(RoomType.SUPERIOR);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : hotel 지점 : Paging O")
    void testSearchByExpression_withHotelCondition_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getHotel()).isEqualTo(Hotel.SEOUL);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 룸 타입 : Paging O")
    void testSearchByExpression_withRoomType_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .roomType(RoomType.SUPERIOR)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getRoomType()).isEqualTo(RoomType.SUPERIOR);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 고객명 : Paging O")
    void testSearchByExpression_withCustomerName_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCustomerName()).isEqualTo("testCustomer1");
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 날짜 : Paging O")
    void testSearchByExpression_withAllDate_withPagination() {

        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCreatedTime()).isBetween(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2024, 1, 2, 23, 59, 59));
    }


    @Test
    @DisplayName("Hotel Review Count : 모든 조건")
    void testCountByExpression_withAllCondition() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .roomType(RoomType.SUPERIOR)
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review Count : 호텔 지점")
    void testCountByExpression_withHotel() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .build();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review Count : 룸 타입")
    void testCountByExpression_withRoomType() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .roomType(RoomType.SUPERIOR)
                .build();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review Count : 고객 명")
    void testCountByExpression_withCustomerName() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review Count : 날짜")
    void testCountByExpression_withDate() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review Count : 조건 X")
    void testCountByExpression_withNoCondition() {
        // Given
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest();

        // When
        Long count = hotelReviewQueryRepository.countByExpression(request);

        // Then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 호텔 지점 : Paging X")
    void testSearchByExpression_withHotel_withoutPagination() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .build();

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 룸 타입 : Paging X")
    void testSearchByExpression_withRoomType_withoutPagination() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .roomType(RoomType.SUPERIOR)
                .build();

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 고객 명 : Paging X")
    void testSearchByExpression_withCustomerName_withoutPagination() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .customerName("testCustomer1")
                .build();

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 날짜 : Paging X")
    void testSearchByExpression_withAllDate_withoutPagination() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 모든 조건 : Paging X")
    void testSearchByExpression_withAllCondition_withoutPagination() {
        // Given
        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .hotel(Hotel.SEOUL)
                .roomType(RoomType.SUPERIOR)
                .customerName("testCustomer1")
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 2, 23, 59, 59))
                .build();

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Hotel Review 다중 검색 : 조건 X : Paging X")
    void testSearchByExpression_withNoCondition_withoutPagination() {
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest();

        List<HotelReviewConditionSearchResponse> result = hotelReviewQueryRepository.searchByExpression(request);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }


}