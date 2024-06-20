package com.team2final.minglecrm.reservation.domain.dining;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DishReservationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dish_reservation_id")
    private DishReservation dishReservation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    private Integer quantity;

    private Integer itemTotalPrice;
}
