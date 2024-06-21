package com.team2final.minglecrm.log.presentation.view;

import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
