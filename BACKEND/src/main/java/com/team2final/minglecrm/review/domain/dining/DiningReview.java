package com.team2final.minglecrm.review.domain.dining;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private Double tasteRating;
    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double atmosphereRating;

    @Column(length = 1000)
    private String review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "dish_reservation_id")
    private DishReservation dishReservation;

    private LocalDateTime createdTime;

    @Builder
    public DiningReview(Double tasteRating, Double kindnessRating, Double cleanlinessRating, Double atmosphereRating, String review, Customer customer, DishReservation dishReservation, LocalDateTime createdTime) {
        this.tasteRating = tasteRating;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.atmosphereRating = atmosphereRating;
        this.review = review;
        this.customer = customer;
        this.dishReservation = dishReservation;
        this.createdTime = createdTime;
    }

}
