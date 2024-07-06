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
public class FrequentCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String employeeName;
    private LocalDate createdDate;
    private String memo;
    private String grade;
    private String address;
    private String gender;
    private Integer visitCnt;
    private LocalDate birth;

    @Builder
    public FrequentCustomer(Long id, String name, String phone, String employeeName, LocalDate createdDate, String memo, String grade, String address, String gender, Integer visitCnt, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.employeeName = employeeName;
        this.createdDate = createdDate;
        this.memo = memo;
        this.grade = grade;
        this.address = address;
        this.gender = gender;
        this.visitCnt = visitCnt;
        this.birth = birth;
    }
}
