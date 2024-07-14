package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.review.domain.hotel.Hotel;
import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import com.team2final.minglecrm.review.domain.hotel.repository.hotelReview.HotelReviewRepository;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewForSummaryResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelReviewServiceTest {

    @Mock
    private HotelReviewRepository hotelReviewRepository;

    @InjectMocks
    private HotelReviewService hotelReviewService;

    @Test
    void createHotelReviewService() {
        HotelReviewService hotelReviewService = new HotelReviewService(hotelReviewRepository);
        assertThat(hotelReviewService).isNotNull();
    }

    @Test
    void searchReviews() {
        // Given
        HotelReviewConditionSearchRequest condition = new HotelReviewConditionSearchRequest();
        int pageNo = 0;

        // Stubbing
        List<HotelReviewConditionSearchResponse> reviews = Collections.singletonList(new HotelReviewConditionSearchResponse());
        Page<HotelReviewConditionSearchResponse> page = new PageImpl<>(reviews);
        when(hotelReviewRepository.searchByExpression(any(), any(PageRequest.class))).thenReturn(page);

        // When
        List<HotelReviewConditionSearchResponse> result = hotelReviewService.searchReviews(condition, pageNo);

        // Then
        assertThat(result.size()).isEqualTo(1);
        verify(hotelReviewRepository, times(1)).searchByExpression(any(), any(PageRequest.class));
    }

    @Test
    void getJoinedHotelReviews() {
        // Given
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime endDate = LocalDateTime.MAX;
        Hotel hotel = Hotel.SEOUL;

        // Mock Data
        HotelReviewForSummaryResponse review = new HotelReviewForSummaryResponse();
        review.setComment("Great stay!");

        // Stubbing
        List<HotelReviewForSummaryResponse> response = Collections.singletonList(review);
        when(hotelReviewRepository.findHotelReviewsByCondition(any())).thenReturn(response);

        // When
        JoinedReviews reviews = hotelReviewService.getJoinedHotelReviews(startDate, endDate, hotel);

        // Then
        assertThat(reviews).isNotNull();
        assertThat(reviews.getJoinedReviews()).contains("0 번째 리뷰 : Great stay!\n");
        verify(hotelReviewRepository, times(1)).findHotelReviewsByCondition(any());
    }

    @Test
    void getHotelReviewMetaData() {
        // Given
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest();
        long expectedRowsNumber = 10L;

        // Stubbing
        when(hotelReviewRepository.countByExpression(any())).thenReturn(expectedRowsNumber);

        // When
        HotelReviewMetaDataResponse response = hotelReviewService.getHotelReviewMetaData(request);

        // Then
        assertThat(response.getRowsNumber()).isEqualTo(expectedRowsNumber);
        verify(hotelReviewRepository, times(1)).countByExpression(any());

    }

    @Test
    public void testGetHotelReviewAverageRatingByPeriod() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        Hotel hotel = Hotel.SEOUL;
        HotelReviewForSummaryResponse review = new HotelReviewForSummaryResponse();
        review.setCleanlinessRating(4.0);
        review.setConvenienceRating(4.0);
        review.setKindnessRating(4.0);
        review.setLocationRating(4.0);
        List<HotelReviewForSummaryResponse> reviews = Collections.singletonList(review);

        when(hotelReviewRepository.findHotelReviewsByCondition(any())).thenReturn(reviews);

        Double averageRating = hotelReviewService.getHotelReviewAverageRatingByPeriod(startDate, endDate, hotel);

        assertThat(averageRating).isEqualTo(4.0);
        verify(hotelReviewRepository, times(1)).findHotelReviewsByCondition(any());
    }

    @Test
    public void testGetHotelReviewsNumberByPeriod() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        Hotel hotel = Hotel.SEOUL;
        List<HotelReviewForSummaryResponse> reviews = Collections.singletonList(new HotelReviewForSummaryResponse());

        when(hotelReviewRepository.findHotelReviewsByCondition(any())).thenReturn(reviews);

        Long reviewCount = hotelReviewService.getHotelReviewsNumberByPeriod(startDate, endDate, hotel);

        assertThat(reviewCount).isEqualTo(1);
        verify(hotelReviewRepository, times(1)).findHotelReviewsByCondition(any());
    }

    @Test
    void testGetAverageRating() {
        // Given
        HotelReviewForSummaryResponse review = new HotelReviewForSummaryResponse();
        review.setKindnessRating(4.0);
        review.setLocationRating(2.0);
        review.setCleanlinessRating(3.0);
        review.setConvenienceRating(3.0);

        // When
        Double averageRating = hotelReviewService.getAverageRating(review);

        // Then
        assertThat(averageRating).isEqualTo(3.0);

    }
}