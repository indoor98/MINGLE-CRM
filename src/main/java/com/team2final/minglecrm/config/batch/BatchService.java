package com.team2final.minglecrm.config.batch;

import com.team2final.minglecrm.controller.batch.response.BatchCustomerResponse;
import com.team2final.minglecrm.controller.batch.response.VisitCustomerResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final CustomerRepository customerRepository;

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public List<BatchCustomerResponse> findNewCustomers(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<Customer> customers = customerRepository.findByCreatedDateBetween(startDate, endDate, pageable);
        return customers.stream()
                .map(customer -> new BatchCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee() != null ? customer.getEmployee().getName() : null,
                        customer.getCreatedDate(),
                        customer.getGrade(),
                        customer.getAddress(),
                        customer.getMemo(),
                        customer.getGender(),
                        customer.getBirth(),
                        customer.getReward() != null ? customer.getReward().getAmount() : null
                ))
                .collect(Collectors.toList());
    }

    public List<VisitCustomerResponse> findCustomersByReservationDateBetween(LocalDate startLocalDate, LocalDate endLocalDate, Pageable pageable) {
        Date startDate = convertToDate(startLocalDate);
        Date endDate = convertToDate(endLocalDate);

        List<Customer> customers = customerRepository.findCustomersByReservationDateBetween(startDate, endDate, pageable).getContent();
        return customers.stream()
                .flatMap(customer -> customer.getRoomReservations().stream()
                        .map(reservation -> VisitCustomerResponse.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .phone(customer.getPhone())
                                .employeeName(customer.getEmployee() != null ? customer.getEmployee().getName() : null)
                                .createdDate(customer.getCreatedDate())
                                .grade(customer.getGrade())
                                .address(customer.getAddress())
                                .memo(customer.getMemo())
                                .gender(customer.getGender())
                                .birth(customer.getBirth())
                                .visitStartDate(reservation.getStartDate())
                                .visitEndDate(reservation.getEndDate())
                                .build()
                        )
                )
                .collect(Collectors.toList());
    }
}