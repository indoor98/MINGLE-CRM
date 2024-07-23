package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.review.domain.hotel.repository.review.HotelReviewQueryRepository;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class HotelReviewServiceTest {

    @Mock
    private HotelReviewQueryRepository hotelReviewQueryRepository;

    @InjectMocks
    private HotelReviewService hotelReviewService;

    @Test
    void testSearchReviews() {
        // Given
        HotelReviewConditionSearchRequest condition = new HotelReviewConditionSearchRequest();
        int pageNo = 1;

        // Stubbing
        HotelReviewConditionSearchResponse reviewResponse = new HotelReviewConditionSearchResponse();
        Page<HotelReviewConditionSearchResponse> page = new PageImpl<>(Collections.singletonList(reviewResponse));

        when(hotelReviewQueryRepository.searchByExpression(condition, PageRequest.of(pageNo, 9))).thenReturn(page);

        // When
        List<HotelReviewConditionSearchResponse> searchResponses = hotelReviewService.searchReviews(condition, pageNo);

        // Then
        assertThat(searchResponses).isNotNull();
        assertThat(searchResponses).hasSize(1);
        verify(hotelReviewQueryRepository, times(1)).searchByExpression(condition, PageRequest.of(pageNo, 9));
    }

    @Test
    void testGetHotelReviewMetaData() {
        // Given
        HotelReviewConditionSearchRequest condition = new HotelReviewConditionSearchRequest();

        // Stubbing
        when(hotelReviewQueryRepository.countByExpression(condition)).thenReturn(1L);

        // When
        HotelReviewMetaDataResponse response = hotelReviewService.getHotelReviewMetaData(condition);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getRowsNumber()).isEqualTo(1L);
        assertThat(response.getPagesNumber()).isEqualTo(1L);
        verify(hotelReviewQueryRepository, times(1)).countByExpression(condition);
    }
}