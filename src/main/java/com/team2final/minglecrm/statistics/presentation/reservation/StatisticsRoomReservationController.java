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


}
