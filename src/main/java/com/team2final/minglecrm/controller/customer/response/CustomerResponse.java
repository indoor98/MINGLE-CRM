package com.team2final.minglecrm.controller.customer.response;


import com.team2final.minglecrm.entity.customer.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class CustomerResponse {

    private final Long id;
    private final String name;
    private final String phone;
    private final String employeeName;
    private final String grade;
    private final String gender;
    private final LocalDate birth;

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmployee().getName(),
                customer.getGrade(),
                customer.getGender(),
                customer.getBirth()
        );
    }
}
