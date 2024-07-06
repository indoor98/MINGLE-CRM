package com.team2final.minglecrm.reservation.dto.dining.response;

import com.team2final.minglecrm.reservation.domain.dining.Dish;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DishResponse {

    private final String name;
    private final Long price;


    public static DishResponse of(Dish dish) {
        return new DishResponse(dish.getName(), dish.getPrice());
    }
}
