package com.team2final.minglecrm.service.reward;

import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherCreateResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.inquiry.InquiryReply;
import com.team2final.minglecrm.entity.reward.Voucher;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.reward.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;


    @Transactional
    public VoucherCreateResponse createVoucher(VoucherCreateRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

        // 바우처 코드 생성 메서드
        int n = 16; // n자리 쿠폰
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = chs[rd.nextInt(chs.length)];
            sb.append(ch);
        }
        String createdVoucherCode = sb.toString();

        Voucher voucher = Voucher.builder()
                .employee(employee)
                .customer(customer)
                .amount(request.getAmount())
                .createDate(LocalDateTime.now())
                .expiredDate(LocalDateTime.now().plusYears(1))
                .voucherCode(createdVoucherCode)
                .build();

        Voucher savedVoucher = voucherRepository.save(voucher);

        return convertToDTO(savedVoucher);
    }

    private VoucherCreateResponse convertToDTO(Voucher voucher){
        return VoucherCreateResponse.builder()
                .customerId(voucher.getCustomer().getId())
                .employeeId(voucher.getEmployee().getId())
                .createDate(voucher.getCreateDate())
                .expireDate(voucher.getExpiredDate())
                .amount(voucher.getAmount())
                .voucherCode(voucher.getVoucherCode())
                .build();
    }
}
