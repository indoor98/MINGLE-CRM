package com.team2final.minglecrm.entity.inquiry;

import com.team2final.minglecrm.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private ActionStatus actionStatus;

    private String actionContent;

    private LocalDateTime date;

    @Builder
    public InquiryAction(Inquiry inquiry, Employee employee, ActionStatus actionStatus, String actionContent, LocalDateTime date) {
        this.inquiry = inquiry;
        this.employee = employee;
        this.actionStatus = actionStatus;
        this.actionContent = actionContent;
        this.date = date;
    }

    public void updateAction(String updateAction, LocalDateTime date, Employee employee, ActionStatus actionStatus) {
        this.actionContent = updateAction;
        this.date = date;
        this.employee = employee;
        this.actionStatus = actionStatus;
    }
}
