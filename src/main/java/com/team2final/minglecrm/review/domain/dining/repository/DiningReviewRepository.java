package com.team2final.minglecrm.review.domain.dining.repository;

import com.team2final.minglecrm.review.domain.dining.DiningReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningReviewRepository extends JpaRepository<DiningReview, Long> {

    Page<DiningReview> findAll(Pageable pageable);
}
