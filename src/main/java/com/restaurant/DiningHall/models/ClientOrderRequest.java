package com.restaurant.DiningHall.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientOrderRequest {
    @JsonAlias("restaurant_id")
    private Integer restaurantId;

    private List<Integer> items = new ArrayList<>();

    private Integer priority;

    @JsonAlias("created_time")
    private Long createdTime;

    @JsonAlias("max_wait")
    private Double maximumWaitTime;
}
