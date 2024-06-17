package com.team2final.minglecrm.statistics.presentation.reservation;

import com.team2final.minglecrm.statistics.service.roomreservation.StatisticsRoomReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class StatisticsRoomReservationController {

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
