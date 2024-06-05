package com.team2final.minglecrm.entity.dining;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;
    private Integer price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dish_reservation_id")
    private DishReservation dishReservation;

}
