package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.review.domain.hotel.repository.review.HotelReviewQueryRepository;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelReviewServiceTest {

    @Mock
    private HotelReviewQueryRepository hotelReviewRepository;

    @InjectMocks
    private HotelReviewService hotelReviewService;

    @Test
    void testSearchReviews() {
        // When
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest();
    }




}