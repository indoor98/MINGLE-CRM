package com.team2final.minglecrm.log.presentation.view;

import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.log.dto.view.request.ViewLogSearchCondition;
import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/view-logs")
public class ViewLogApi {

    private final ViewLogService viewLogService;

    @GetMapping
    public ResponseEntity<List<ViewLogResponse>> getAllLogs() {
        List<ViewLogResponse> viewAllLogs = viewLogService.findAllLogs();
        return ResponseEntity.ok(viewAllLogs);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ViewLogResponse>> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "employeeName", required = false) String employeeName,
            @RequestParam(value = "grade", required = false) String employeeGrade,
            @RequestParam(value = "employeeEmail", required = false) String employeeEmail,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "customerGrade", required = false) String customerGrade,
            @RequestParam(value = "customerEmail", required = false) String customerEmail
            ) {
        ViewLogSearchCondition condition = new ViewLogSearchCondition(
                employeeName, employeeGrade, employeeEmail, customerName, customerGrade, customerEmail
        );

        Page<ViewLogResponse> viewLogResponses = viewLogService.search(pageable, condition);
        return ResponseEntity.ok(viewLogResponses);
    }

}
