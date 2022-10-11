package com.restaurant.DiningHall.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.restaurant.DiningHall.constants.CookingApparatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Food {
    @JsonAlias("id")
    private int id;

    @JsonAlias("name")
    private String name;

    @JsonAlias("preparation-time")
    private long preparationTime;

    @JsonAlias("complexity")
    private int complexity;

    @JsonAlias("cooking-apparatus")
    private CookingApparatus cookingApparatus;
}