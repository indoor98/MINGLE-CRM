package com.team2final.minglecrm.statistics.controller.reservation;

import com.team2final.minglecrm.statistics.service.roomreservation.StatisticsRoomReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class StatisticsRoomReservationApi {

    private final StatisticsRoomReservationService statisticsRoomReservationService;


}
