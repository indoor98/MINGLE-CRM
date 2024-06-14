package com.team2final.minglecrm.controller.statistics.reservation;

import com.team2final.minglecrm.controller.statistics.reservation.response.StatisticsRoomReservationResponse;
import com.team2final.minglecrm.service.statistics.roomreservation.StatisticsRoomReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class StatisticsRoomReservationApi {

    private final StatisticsRoomReservationService statisticsRoomReservationService;

//    @GetMapping("/monthly-reserved")
//    public List<StatisticsRoomReservationResponse> getMonthlyReservations(@RequestParam("year") Integer year,
//                                                                          @RequestParam("month") Integer month,
//                                                                          @PageableDefault(sort = "id") Pageable pageable) {
//        try {
//            return statisticsRoomReservationService.findReservationsByMonthAndYear(year, month, pageable);
//        } catch (Exception e) {
//             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "정보를 조회할 수 없습니다.");
//        }
//    }
}
