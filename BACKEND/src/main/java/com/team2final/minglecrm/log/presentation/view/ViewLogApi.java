package com.team2final.minglecrm.log.presentation.view;

import com.team2final.minglecrm.log.dto.view.request.ViewLogSearchCondition;
import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ModelAttribute ViewLogSearchCondition searchCondition,
            Pageable pageable

    ) {
        ViewLogSearchCondition condition = ViewLogSearchCondition.of(searchCondition.getEmployeeName(),
                searchCondition.getEmployeeGrade(),
                searchCondition.getEmployeeEmail(),
                searchCondition.getCustomerName(),
                searchCondition.getCustomerGrade(),
                searchCondition.getCustomerEmail(),
                searchCondition.getStartDate(),
                searchCondition.getEndDate());

        Page<ViewLogResponse> viewLogResponses = viewLogService.search(pageable, condition);

        return ResponseEntity.ok(viewLogResponses);
    }
}
