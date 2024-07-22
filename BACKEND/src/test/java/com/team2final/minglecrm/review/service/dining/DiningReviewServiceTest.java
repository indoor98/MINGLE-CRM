package com.team2final.minglecrm.review.service.dining;

import com.team2final.minglecrm.review.domain.dining.repository.review.DiningReviewQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiningReviewServiceTest {

    @Mock
    private DiningReviewQueryRepository diningReviewQueryRepository;

    @InjectMocks
    private DiningReviewService diningReviewService;

    @Test
    void testSearchDiningReviews() {

    }

    @Test
    void testGetDiningReviewMetaData() {

    }
}