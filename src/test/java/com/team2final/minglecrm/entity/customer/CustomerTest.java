package com.team2final.minglecrm.entity.customer;

import com.team2final.minglecrm.controller.customer.request.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void updateCustomer() {
        Customer customer = Customer.builder()
                .id(1L)
                .grade("VIP")
                .birth(LocalDate.now())
                .name("kim dongwook")
                .memo("good boy")
                .phone("010-1234-1234")
                .address("seoul")
                .build();




    }


}