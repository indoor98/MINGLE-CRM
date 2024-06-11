package com.team2final.minglecrm.entity.event;

import com.team2final.minglecrm.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime sentDate;

    private Long sendCount;

    private Long readCount;

    @Builder
    public Event(String title, String content, Employee employee, LocalDateTime sentDate, Long sendCount) {
        this.title = title;
        this.content = content;
        this.employee = employee;
        this.sentDate = sentDate;
        this.sendCount = sendCount;
        this.readCount = 0L;
    }

    public Long increaseReadCount() {
        this.readCount += 1;
        return readCount;
    }
}
