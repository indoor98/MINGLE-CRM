package com.team2final.minglecrm.entity.dining;

import com.team2final.minglecrm.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "dish_reservation_id")
    private DishReservation dishReservation;

    @Builder
    public DiningReview(Double tasteRating, Double kindnessRating, Double cleanlinessRating, Double atmosphereRating, String review, Customer customer, DishReservation dishReservation) {
        this.tasteRating = tasteRating;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.atmosphereRating = atmosphereRating;
        this.review = review;
        this.customer = customer;
        this.dishReservation = dishReservation;
    }

}
