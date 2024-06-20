package com.team2final.minglecrm.review.domain.dining.repository;

import com.team2final.minglecrm.review.domain.dining.DiningReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiningReviewRepository extends JpaRepository<DiningReview, Long>, DiningReviewQueryDslRepository {

    Page<DiningReview> findAll(Pageable pageable);
    List<DiningReview> findDiningReviewByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
