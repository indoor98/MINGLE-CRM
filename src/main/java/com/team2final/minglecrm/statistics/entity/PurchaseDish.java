package com.team2final.minglecrm.statistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class PurchaseDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate purchaseDate;
    private Integer quantity;
    private Long amount;

    @Builder
    public PurchaseDish(Long id, String name, LocalDate purchaseDate, Integer quantity, Long amount) {
        this.id = id;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
        this.amount = amount;
    }
}
