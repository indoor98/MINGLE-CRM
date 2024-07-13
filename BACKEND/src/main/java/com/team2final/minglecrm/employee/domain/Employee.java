package com.team2final.minglecrm.employee.domain;


import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE employee SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String authority;

    private LocalDateTime createdDate;

    private Boolean isDeleted;

    @Builder
    public Employee(String name, String email, String password, String authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.createdDate = LocalDateTime.now();
        this.isDeleted = false;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void updateEmployeeInfo(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void createEmployee(SignUpRequest signUpRequest) {
        this.name = signUpRequest.getName();
        this.email = signUpRequest.getEmail();
        this.password = signUpRequest.getPassword();
        this.authority = signUpRequest.getAuthority();
        this.createdDate = LocalDateTime.now();
        this.isDeleted = false;
    }
}
