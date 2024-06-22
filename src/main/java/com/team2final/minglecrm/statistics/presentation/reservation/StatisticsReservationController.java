package com.team2final.minglecrm.statistics.presentation.reservation;

import com.team2final.minglecrm.statistics.dto.response.reservation.ByYearReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.DailyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.MonthlyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.WeeklyReservationResponse;
import com.team2final.minglecrm.statistics.service.reservation.StatisticsReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic/reservation")
public class StatisticsReservationController {

    private final StatisticsReservationService statisticsReservationService;

    // // 연 별 예약 수 조회
    @GetMapping("/by-year-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<ByYearReservationResponse>> getByYearReservationCount(Pageable pageable) {
        List<ByYearReservationResponse> result = statisticsReservationService.getAllByYearReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 월 별 예약 수 조회
    @GetMapping("/monthly-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<MonthlyReservationResponse>> getMonthlyReservationCount(Pageable pageable) {
        List<MonthlyReservationResponse> result = statisticsReservationService.getAllMonthlyReservation(pageable);
        return ResponseEntity.ok(result);
    }


    // // 주 별 예약 수 조회
    @GetMapping("/weekly-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<WeeklyReservationResponse>> getWeeklyReservationCount(Pageable pageable) {
        List<WeeklyReservationResponse> result = statisticsReservationService.getAllWeeklyReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 일 별 예약 수 조회
    @GetMapping("/daily-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<DailyReservationResponse>> getDailyReservationCount(Pageable pageable) {
        List<DailyReservationResponse> result = statisticsReservationService.getAllDailyReservation(pageable);
        return ResponseEntity.ok(result);
    }
}
