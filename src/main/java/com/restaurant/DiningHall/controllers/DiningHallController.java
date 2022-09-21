package com.restaurant.DiningHall.controllers;


import com.restaurant.DiningHall.models.FinishedOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distribution")
public class DiningHallController {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveFinishedOrder(@RequestBody FinishedOrder finishedOrder) {
        System.out.println("Received " + finishedOrder.toString());
    }
}
