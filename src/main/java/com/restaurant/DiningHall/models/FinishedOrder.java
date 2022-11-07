package com.restaurant.DiningHall.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class FinishedOrder {
    @JsonAlias("order_id")
    private int orderId;

    @JsonAlias("table_id")
    private int tableId;

    @JsonAlias("waiter_id")
    private Integer waiterId;

    @JsonAlias("items")
    private List<Integer> items = new ArrayList<>();

    @JsonAlias("priority")
    private int priority;

    @JsonAlias("max_wait")
    private double maxWait;

    @JsonAlias("pick_up_time")
    private long pickUpTime;

    @JsonAlias("cooking_time")
    private long cookingTime;

    @JsonAlias("cooking_details")
    private List<CookingDetails> cookingDetails;

    @JsonIgnore
    private Instant servingTime;
}
