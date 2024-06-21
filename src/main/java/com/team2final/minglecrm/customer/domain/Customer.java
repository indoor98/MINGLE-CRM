package com.team2final.minglecrm.customer.domain;


import com.team2final.minglecrm.customer.dto.request.CustomerMemoCreateAndUpdateRequest;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import com.team2final.minglecrm.reward.domain.Reward;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    private String email;

    private String grade;

    private String phone;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String memo;

    private String gender;

    private LocalDate birth;

    private LocalDate createdDate;

    @OneToOne(mappedBy = "customer")
    private Reward reward;

    private Boolean isDeleted;

    private Integer visitCnt;

    @OneToMany(mappedBy = "customer")
    private List<RoomReservation> roomReservations;

    public void updateCustomerDetail(CustomerUpdateRequest customerUpdateRequest) {
        this.name = customerUpdateRequest.getName();
        this.gender = customerUpdateRequest.getGender();
        this.memo = customerUpdateRequest.getMemo();
        this.phone = customerUpdateRequest.getPhone();
        this.address = customerUpdateRequest.getAddress();
    }

    public void deleteCustomer() {
        this.isDeleted = true;
    }

    public void updateCustomerReservationDetail(String memo, String name) {
        this.memo = memo;
        this.name = name;
    }

    @Builder
    public Customer(Long id, String name, String grade, String email, String phone, String address, Employee employee, String memo, String gender, LocalDate birth, Reward reward, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employee = employee;
        this.memo = memo;
        this.gender = gender;
        this.birth = birth;
        this.reward = reward;
        this.isDeleted = isDeleted;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void createAndUpdateMemo(CustomerMemoCreateAndUpdateRequest memoCreateAndUpdateRequest) {
        this.memo = memoCreateAndUpdateRequest.getMemo();
    }
}
