package com.team2final.minglecrm.review.service.ai;

import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.domain.SummaryType;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.dining.repository.review.DiningReviewQueryRepository;
import com.team2final.minglecrm.review.domain.dining.repository.summary.DiningReviewSummaryQueryRepository;
import com.team2final.minglecrm.review.domain.dining.repository.summary.DiningReviewSummaryRepository;
import com.team2final.minglecrm.review.domain.hotel.HotelReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.repository.review.HotelReviewQueryRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.summary.HotelReviewSummaryQueryRepository;
import com.team2final.minglecrm.review.domain.hotel.repository.summary.HotelReviewSummaryRepository;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewSummaryRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewSummaryRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;

    private final HotelReviewQueryRepository hotelReviewQueryRepository;
    private final HotelReviewSummaryRepository hotelReviewSummaryRepository;
    private final HotelReviewSummaryQueryRepository hotelReviewSummaryQueryRepository;

    private final DiningReviewQueryRepository diningReviewQueryRepository;
    private final DiningReviewSummaryRepository diningReviewSummaryRepository;
    private final DiningReviewSummaryQueryRepository diningReviewSummaryQueryRepository;

    /* 해당 조건에 맞는 ai 호텔 리뷰 요약이 존재하는 경우 */
    public HotelReviewSummaryResponse getHotelReviewSummary(HotelReviewSummaryRequest condition) {
        HotelReviewSummary hotelReviewSummary = hotelReviewSummaryQueryRepository.searchByCondition(condition);
        return hotelReviewSummary != null ? HotelReviewSummary.of(hotelReviewSummary) : null;
    }

    /* 호텔 리뷰 요약 생성 */
    public HotelReviewSummaryResponse createHotelReviewSummary(HotelReviewSummaryRequest condition) {

        HotelReviewConditionSearchRequest request = HotelReviewConditionSearchRequest.builder()
                .startDate(condition.getStartDate())
                .endDate(condition.getEndDate())
                .hotel(condition.getHotel())
                .build();

        List<HotelReviewConditionSearchResponse> reviews = hotelReviewQueryRepository.searchByExpression(request);

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("요약할 리뷰가 없습니다");
        }

        // 평균 평점 계산
        Double averageRating = calculateOverallAverageRatingForHotel(reviews);

        /* 리뷰 String Join */
        StringBuilder JoinedReview = new StringBuilder();
        for (int i=0; i < reviews.size() ; i++) {
            JoinedReview.append(i)
                    .append(" 번째 리뷰 : ")
                    .append(reviews.get(i).getComment())
                    .append("\n");
        }

        // 리뷰 요약 생성

        SummaryType summaryType = condition.getSummaryType();
        String systemMessageContent = generateSystemMessage(condition.getSummaryType());
        SystemMessage systemMessage = new SystemMessage(systemMessageContent);
        UserMessage userMessage = new UserMessage(JoinedReview.toString());
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String summaryContent = chatClient.prompt(prompt).call().content();

        HotelReviewSummary summary = HotelReviewSummary.builder()
                .startDate(condition.getStartDate())
                .endDate(condition.getEndDate())
                .hotel(condition.getHotel())
                .summaryType(condition.getSummaryType())
                .summary(summaryContent)
                .averageRating(averageRating)
                .reviewAmount((long) reviews.size())
                .build();

        hotelReviewSummaryRepository.save(summary);

        return HotelReviewSummary.of(summary);
    }

    /* 다이닝 리뷰 요약 조회 */
    public DiningReviewSummaryResponse getDiningReviewSummary(DiningReviewSummaryRequest condition) {
        DiningReviewSummary diningReviewSummary = diningReviewSummaryQueryRepository.searchByCondition(condition);
        return diningReviewSummary != null ? DiningReviewSummary.of(diningReviewSummary) : null;
    }

    /* 다이닝 리뷰 ai 요약 생성 */
    public DiningReviewSummaryResponse createDiningReviewSummary(DiningReviewSummaryRequest condition) {

        DiningReviewConditionSearchRequest request = DiningReviewConditionSearchRequest.builder()
                .startDate(condition.getStartDate())
                .endDate(condition.getEndDate())
                .restaurant(condition.getRestaurant())
                .build();

        /* 요약을 위한 리뷰 준비 */
        List<DiningReviewConditionSearchResponse> reviews = diningReviewQueryRepository.searchByExpression(request);

        /* 평점 계산 */
        Double averageRating = calculateOverallAverageRatingForDining(reviews);

        /* 요약 내용 JOIN */
        StringBuilder JoinedReview = new StringBuilder();
        for (int i=0; i < reviews.size() ; i++) {
            JoinedReview.append(i)
                    .append(" 번째 리뷰 : ")
                    .append(reviews.get(i).getReview())
                    .append("\n");
        }

        /* 다이닝 리뷰 요약 생성 */
        SummaryType summaryType = condition.getSummaryType();
        String systemMessageContent = generateSystemMessage(condition.getSummaryType());
        SystemMessage systemMessage = new SystemMessage(systemMessageContent);
        UserMessage userMessage = new UserMessage(JoinedReview.toString());
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String summaryContent = chatClient.prompt(prompt).call().content();

        DiningReviewSummary summary = DiningReviewSummary.builder()
                .startDate(condition.getStartDate())
                .endDate(condition.getEndDate())
                .summaryType(condition.getSummaryType())
                .restaurant(condition.getRestaurant())
                .summary(summaryContent)
                .averageRating(averageRating)
                .reviewAmount((long) reviews.size())
                .build();

        diningReviewSummaryRepository.save(summary);

        return DiningReviewSummary.of(summary);
    }


    private String generateSystemMessage(SummaryType summaryType) {
        if (summaryType.equals(SummaryType.POSITIVE)) {
            return "너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 긍정적인 내용 위주로 요약해줘 \n";
        } else {
            return "너는 리뷰들을 하나의 문단을 요약해주는 훌륭한 챗 봇이야 리뷰들을 부정적인 내용 위주로 요약해줘 \n";
        }
    }


    private static double calculateOverallAverageRatingForHotel(List<HotelReviewConditionSearchResponse> reviews) {
        double totalSum = reviews.stream()
                .flatMapToDouble(review -> Stream.of(
                        review.getKindnessRating(),
                        review.getCleanlinessRating(),
                        review.getConvenienceRating(),
                        review.getLocationRating()
                ).mapToDouble(Double::doubleValue))
                .sum();

        long totalCount = reviews.size() * 4L; // 각 리뷰마다 4개의 레이팅이 있다고 가정

        return totalCount > 0 ? totalSum / totalCount : 0.0;
    }

    private static double calculateOverallAverageRatingForDining(List<DiningReviewConditionSearchResponse> reviews) {
        double totalSum = reviews.stream()
                .flatMapToDouble(review -> Stream.of(
                        review.getKindnessRating(),
                        review.getCleanlinessRating(),
                        review.getAtmosphereRating(),
                        review.getTasteRating()
                ).mapToDouble(Double::doubleValue))
                .sum();

        long totalCount = reviews.size() * 4L; // 각 리뷰마다 4개의 레이팅이 있다고 가정

        return totalCount > 0 ? totalSum / totalCount : 0.0;
    }
}
