package com.team2final.minglecrm.service.statistics.customer;

import com.team2final.minglecrm.controller.statistics.customer.response.StatisticsCustomerResponse;
import com.team2final.minglecrm.controller.statistics.customer.response.VisitCustomerResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.persistence.repository.statistics.StatisticsCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class StatisticsCustomerService {

    private final StatisticsCustomerRepository statisticsCustomerRepository;

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public List<StatisticsCustomerResponse> findNewCustomers(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        Page<Customer> customers = statisticsCustomerRepository.findByCreatedDateBetween(startDate, endDate, pageable);
        return customers.stream()
                .map(customer -> new StatisticsCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee() != null ? customer.getEmployee().getName() : null,
                        customer.getCreatedDate(),
                        customer.getGrade(),
                        customer.getAddress(),
                        customer.getMemo(),
                        customer.getGender(),
                        customer.getVisitCnt(),
                        customer.getBirth(),
                        customer.getReward() != null ? customer.getReward().getAmount() : null
                ))
                .collect(Collectors.toList());
    }

    public List<VisitCustomerResponse> findCustomersByReservationDateBetween(LocalDate startLocalDate, LocalDate endLocalDate, Pageable pageable) {
        Page<Customer> customers = statisticsCustomerRepository.findCustomersByReservationDateBetween(startLocalDate, endLocalDate, pageable);
        return customers.stream()
                .map(customer -> customer.getRoomReservations().stream()
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
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<StatisticsCustomerResponse> findByVisitCntGreaterThan(Integer visitCnt, Pageable pageable) {
        Page<Customer> customers = statisticsCustomerRepository.findByVisitCntGreaterThan(visitCnt, pageable);
        return customers.stream()
                .map(customer -> new StatisticsCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee() != null ? customer.getEmployee().getName() : null,
                        customer.getCreatedDate(),
                        customer.getGrade(),
                        customer.getAddress(),
                        customer.getMemo(),
                        customer.getGender(),
                        customer.getVisitCnt(),
                        customer.getBirth(),
                        customer.getReward() != null ? customer.getReward().getAmount() : null
                ))
                .collect(Collectors.toList());
    }
}