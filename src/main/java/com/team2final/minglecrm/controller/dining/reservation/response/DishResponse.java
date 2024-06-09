package com.team2final.minglecrm.controller.dining.reservation.response;

import com.team2final.minglecrm.entity.dining.Dish;
import lombok.Builder;
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
