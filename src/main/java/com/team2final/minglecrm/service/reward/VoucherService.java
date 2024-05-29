package com.team2final.minglecrm.service.reward;

import com.team2final.minglecrm.controller.reward.request.VoucherCreateRequest;
import com.team2final.minglecrm.controller.reward.response.VoucherApprovalResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherHistoryResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherRequestResponse;
import com.team2final.minglecrm.controller.reward.response.VoucherResponse;
import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.Voucher;
import com.team2final.minglecrm.entity.reward.VoucherHistory;
import com.team2final.minglecrm.persistence.repository.customer.CustomerRepository;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.repository.reward.VoucherHistoryRepository;
import com.team2final.minglecrm.persistence.repository.reward.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final VoucherHistoryRepository voucherHistoryRepository;

    @Transactional
    public VoucherResponse createVoucher(VoucherCreateRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

        String createdVoucherCode = generateUniqueVoucherCode();

        Voucher voucher = Voucher.builder()
                .employee(employee)
                .customer(customer)
                .amount(request.getAmount())
                .createDate(LocalDateTime.now())
                .expiredDate(LocalDateTime.now().plusYears(1))
                .voucherCode(createdVoucherCode)
                .build();

        voucherRepository.save(voucher);

        return VoucherResponse.of(voucher);
    }

    // voucher code 생성 메서드
    private String generateUniqueVoucherCode() {
        int n = 16; // n자리 쿠폰
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random rd = new Random();
        String createdVoucherCode="";
        boolean isUnique = false;

        while (!isUnique) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                char ch = chs[rd.nextInt(chs.length)];
                sb.append(ch);
            }
            createdVoucherCode = sb.toString();

            // 데이터베이스에서 중복 체크
            isUnique = !voucherRepository.existsByVoucherCode(createdVoucherCode);
        }

        return createdVoucherCode;
    }

    @Transactional
    public List<VoucherResponse> voucherList(){
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResponse::of)
                .collect(Collectors.toList());
    }


    @Transactional
    public VoucherRequestResponse requestVoucher(Long voucherId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Employee employee = employeeRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 바우처를 찾을 수 없습니다."));

        VoucherHistory voucherHistory = VoucherHistory.builder()
                .voucher(voucher)
                .customer(voucher.getCustomer())
                .employeeStaff(employee)
                .requestDate(LocalDateTime.now())  // 현재 시간을 requestDate로 설정
                .build();

        voucherHistoryRepository.save(voucherHistory);

        return VoucherRequestResponse.of(voucherHistory);
    }
    
    @Transactional
    public VoucherApprovalResponse approveVoucher(Long voucherId){

        VoucherHistory voucherHistory = voucherHistoryRepository.findByVoucherId(voucherId).
                orElseThrow(() -> new RuntimeException("해당 ID의 바우처의 히스토리를 찾을 수 없습니다."));

        voucherHistory.approveVoucher();
        voucherHistoryRepository.save(voucherHistory);
        return VoucherApprovalResponse.of(voucherHistory);
    }

}
