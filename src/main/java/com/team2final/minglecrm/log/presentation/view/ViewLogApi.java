package com.team2final.minglecrm.log.presentation.view;

import com.team2final.minglecrm.log.dto.view.request.ViewLogSearchCondition;
import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/view-logs")
public class ViewLogApi {

    private final ViewLogService viewLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<ViewLogResponse>> getAllLogs() {
        List<ViewLogResponse> viewAllLogs = viewLogService.findAllLogs();
        return ResponseEntity.ok(viewAllLogs);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<Page<ViewLogResponse>> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "employeeName", required = false) String employeeName,
            @RequestParam(value = "employeeEmail", required = false) String employeeEmail,
            @RequestParam(value = "employeeGrade", required = false) String employeeGrade,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "customerEmail", required = false) String customerEmail,
            @RequestParam(value = "customerGrade", required = false) String customerGrade,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        ViewLogSearchCondition condition = new ViewLogSearchCondition(
                employeeName, employeeGrade, employeeEmail, customerName, customerGrade, customerEmail, startDate, endDate
        );

        Page<ViewLogResponse> viewLogResponses = viewLogService.search(pageable, condition);
        return ResponseEntity.ok(viewLogResponses);
    }
}
