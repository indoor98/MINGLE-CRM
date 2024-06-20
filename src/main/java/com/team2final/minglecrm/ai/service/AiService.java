package com.team2final.minglecrm.ai.service;

import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.vo.DiningReviewForSummary;
import com.team2final.minglecrm.ai.dto.vo.HotelReviewForSummary;
import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewRepository;
import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewSummaryRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.HotelReviewQueryDslRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.HotelReviewRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.HotelReviewSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final HotelReviewRepository hotelReviewRepository;
    private final HotelReviewSummaryRepository hotelReviewSummaryRepository;
    private final DiningReviewRepository diningReviewRepository;
    private final DiningReviewSummaryRepository diningReviewSummaryRepository;



    private String generateSystemMessage(SummaryType summaryType) {
        if (summaryType.equals(SummaryType.POSITIVE)) {
            return "너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 긍정적인 내용 위주로 요약해줘 \n";
        } else {
            return "너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 부정적인 내용 위주로 요약해줘 \n";
        }
    }

    private void saveHotelSummary(JoinedReviews joinedHotelReviews, SummaryType summaryType, String summaryContent) {
        HotelReviewSummary summary = HotelReviewSummary.builder()
                .summary(summaryContent)
                .summaryType(summaryType)
                .startDate(joinedHotelReviews.getStartDate())
                .endDate(joinedHotelReviews.getEndDate())
                .build();

        hotelReviewSummaryRepository.save(summary);
    }

    private void saveDiningSummary(JoinedReviews joinedDiningReviews, SummaryType summaryType, String summaryContent) {
        DiningReviewSummary summary = DiningReviewSummary.builder()
                .summary(summaryContent)
                .summaryType(summaryType)
                .startDate(joinedDiningReviews.getStartDate())
                .endDate(joinedDiningReviews.getEndDate())
                .build();

        diningReviewSummaryRepository.save(summary);
    }

    public String createHotelReviewSummary(JoinedReviews joinedHotelReviews, SummaryType summaryType) {
        String systemMessageContent = generateSystemMessage(summaryType);
        SystemMessage systemMessage = new SystemMessage(systemMessageContent);

        UserMessage userMessage = new UserMessage(joinedHotelReviews.getJoinedReviews());
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String answer = chatClient.prompt(prompt).call().content();

        saveHotelSummary(joinedHotelReviews, summaryType, answer);
        return answer;
    }

    public String createDtiningReviewSummary (JoinedReviews joinedDiningReviews, SummaryType summaryType) {
        String systemMessageContent = generateSystemMessage(summaryType);
        SystemMessage systemMessage = new SystemMessage(systemMessageContent);

        UserMessage userMessage = new UserMessage(joinedDiningReviews.getJoinedReviews());
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String answer = chatClient.prompt(prompt).call().content();

        saveDiningSummary(joinedDiningReviews, summaryType, answer);
        return answer;
    }

    public HotelReviewSummaryResponse getLatestHotelReviewSummary(SummaryType summaryType) {
        List<HotelReviewSummary> entities =  hotelReviewSummaryRepository.findHotelReviewSummariesBySummaryTypeOrderByStartDateDesc(summaryType);
        return HotelReviewSummaryResponse.of(entities.get(0));
    }

    public DiningReviewSummaryResponse getLatestDiningReviewSummary(SummaryType summaryType) {
        List<DiningReviewSummary> entities = diningReviewSummaryRepository.findDiningReviewSummariesBySummaryTypeOrderByStartDateDesc(summaryType);
        return DiningReviewSummaryResponse.of(entities.get(0));
    }

}
