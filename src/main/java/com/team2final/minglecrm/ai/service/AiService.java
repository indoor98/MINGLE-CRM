package com.team2final.minglecrm.ai.service;

import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.vo.DiningReviewForSummary;
import com.team2final.minglecrm.ai.dto.vo.HotelReviewForSummary;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewSummaryRepository;
import com.team2final.minglecrm.review.domain.dining.repository.queryDsl.DiningReviewRepositoryCustom;
import com.team2final.minglecrm.review.domain.hotel.repository.queryDsl.HotelReviewRepositoryCustom;
import com.team2final.minglecrm.review.domain.hotel.repository.queryDsl.HotelReviewSummaryRepository;
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
    private final HotelReviewRepositoryCustom hotelReviewRepositoryCustom;
    private final HotelReviewSummaryRepository hotelReviewSummaryRepository;
    private final DiningReviewRepositoryCustom diningReviewRepositoryCustom;
    private final DiningReviewSummaryRepository diningReviewSummaryRepository;

    public String createHotelReviewSummary(LocalDateTime startDate, SummaryType summaryType) {

        List<HotelReviewForSummary> reviews = hotelReviewRepositoryCustom.findAllByStartDateCondition(startDate);
        String question = "";

        for (int i=0; i < reviews.size() ; i++) {
            question += i + " 번째 리뷰 : " + reviews.get(i).getContent() + "\n";
        }

        SystemMessage systemMessage;

        if (summaryType.equals(SummaryType.POSITIVE)) {
            systemMessage = new SystemMessage("너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 긍정적인 내용 위주로 요약해줘 \n");
        }
        else if (summaryType.equals(SummaryType.NEGATIVE)) {
            systemMessage = new SystemMessage("너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 부정적인 내용 위주로 요약해줘 \n");
        } else {
            throw new IllegalStateException("잘못된 요청입니다");
        }

        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String answer = chatClient.prompt(prompt).call().content();

        HotelReviewSummary summary = HotelReviewSummary.builder()
                .summary(answer)
                .summaryType(summaryType)
                .startDate(startDate)
                .build();

        hotelReviewSummaryRepository.save(summary);
        return answer;
    }

    public String createDiningReviewSummary(LocalDateTime startDate, SummaryType summaryType) {

        List<DiningReviewForSummary> reviews = diningReviewRepositoryCustom.findAllByStartDateCondition(startDate);
        String question = "";

        for (int i=0; i < reviews.size() ; i++) {
            question += i + " 번째 리뷰 : " + reviews.get(i).getContent() + "\n";
        }

        SystemMessage systemMessage;

        if (summaryType.equals(SummaryType.POSITIVE)) {
            systemMessage = new SystemMessage("너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 긍정적인 내용 위주로 요약해줘 \n");
        }
        else if (summaryType.equals(SummaryType.NEGATIVE)) {
            systemMessage = new SystemMessage("너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 부정적인 내용 위주로 요약해줘 \n");
        } else {
            throw new IllegalStateException("잘못된 요청입니다");
        }

        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String answer = chatClient.prompt(prompt).call().content();

        DiningReviewSummary summary = DiningReviewSummary.builder()
                .summary(answer)
                .summaryType(summaryType)
                .startDate(startDate)
                .build();

        diningReviewSummaryRepository.save(summary);
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
