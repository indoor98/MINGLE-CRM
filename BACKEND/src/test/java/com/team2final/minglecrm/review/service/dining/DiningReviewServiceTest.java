package com.team2final.minglecrm.review.service.dining;

import com.team2final.minglecrm.review.domain.dining.repository.review.DiningReviewQueryRepository;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewMetaDataResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class DiningReviewServiceTest {

    @Mock
    private DiningReviewQueryRepository diningReviewQueryRepository;

    @InjectMocks
    private DiningReviewService diningReviewService;

    @Test
    void testSearchDiningReviews() {
        // Given
        DiningReviewConditionSearchRequest condition = new DiningReviewConditionSearchRequest();
        int pageNo = 1;

        Page<DiningReviewConditionSearchResponse> page = new PageImpl<>(Collections.singletonList(new DiningReviewConditionSearchResponse()));
        when(diningReviewQueryRepository.searchByExpression(condition, PageRequest.of(pageNo, 9))).thenReturn(page);

        // When
        List<DiningReviewConditionSearchResponse> response = diningReviewService.searchDiningReviews(condition, pageNo);

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        verify(diningReviewQueryRepository, times(1)).searchByExpression(condition, PageRequest.of(pageNo, 9));

    }

    @Test
    void testGetDiningReviewMetaData() {
        // Given
        DiningReviewConditionSearchRequest condition = new DiningReviewConditionSearchRequest();

        when(diningReviewQueryRepository.countByExpression(condition)).thenReturn(19L);

        // When
        DiningReviewMetaDataResponse response = diningReviewService.getDiningReviewMetaData(condition);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getPagesNumber()).isEqualTo(3L);
        assertThat(response.getRowsNumber()).isEqualTo(19L);
        verify(diningReviewQueryRepository, times(1)).countByExpression(condition);
    }
}