package com.restaurant.DiningHall.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Food {
    @JsonAlias("id")
    private Long id;

    @JsonAlias("name")
    private String name;

    @JsonAlias("preparation-time")
    private Long preparation_time;

    @JsonAlias("complexity")
    private int complexity;

    @JsonAlias("cooking-apparatus")
    private String cookingApparatus;

    public Long getId() {
        return id;
    }


    public Long getPreparation_time() {
        return preparation_time;
    }
}