package com.restaurant.DiningHall.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientSubOrderRatingResponse {
    @JsonAlias("restaurant_id")
    private Integer restaurantId;

    @JsonAlias("prepared_orders")
    private Integer preparedOrders;

    @JsonAlias("restaurant_avg_rating")
    private Double restaurantAvgRating;
}
