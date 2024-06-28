package com.team2final.minglecrm.registration.domain;

import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;
    private String requestedRole;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;


    @Builder
    public Registration(String name, String password, String email, String requestedRole, RequestStatus status) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.requestedRole = requestedRole;
        this.status = status;
    }

    public void changeStatus() {
        this.status = RequestStatus.APPROVED;
//        this.manager =
    }
}
