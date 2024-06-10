package com.team2final.minglecrm.entity.dining;

import com.team2final.minglecrm.persistence.repository.dining.DishReservationDetailRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;
    private Long price;

    @OneToMany
    private List<DishReservationDetail> dishReservations;

}
