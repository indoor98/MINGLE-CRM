package com.team2final.minglecrm.statistics.domain;

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
public class PurchaseList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;
    private String customerName;
    private String customerGrade;
    private String customerGender;
    private String consumeType;
    private String dishName;
    private String roomType;

    @Builder
    public PurchaseList(Long id, LocalDate purchaseDate, String customerName, String customerGrade, String customerGender, String consumeType, String dishName, String roomType) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.customerName = customerName;
        this.customerGrade = customerGrade;
        this.customerGender = customerGender;
        this.consumeType = consumeType;
        this.dishName = dishName;
        this.roomType = roomType;
    }

}

