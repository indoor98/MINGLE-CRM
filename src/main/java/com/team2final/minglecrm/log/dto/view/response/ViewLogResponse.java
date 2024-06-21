package com.team2final.minglecrm.log.dto.view.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.log.domain.ViewLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ViewLogResponse {

    private final Long id;

    private final String EmployeeName;

    private final String EmployeeGrade;

    private final String customerName;

    private final String customerGrade;

    private final LocalDateTime viewTime;

    private final Integer viewCount;


    public static ViewLogResponse from(ViewLog viewLog) {
        return new ViewLogResponse(
                viewLog.getId(),
                viewLog.getEmployee().getName(),
                viewLog.getEmployee().getAuthority(),
                viewLog.getCustomer().getName(),
                viewLog.getCustomer().getGrade(),
                viewLog.getViewTime(),
                viewLog.getViewCount()
        );
    }

}
