package com.team2final.minglecrm.service.dining.review;

import com.team2final.minglecrm.persistence.repository.dining.DiningReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiningReviewService {

    private final DiningReviewRepository diningReviewRepository;


}
