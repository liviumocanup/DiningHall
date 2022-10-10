package com.restaurant.DiningHall.controllers;


import com.restaurant.DiningHall.models.FinishedOrder;
import com.restaurant.DiningHall.service.DiningHallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distribution")
@Slf4j
public class DiningHallController {
    private final DiningHallService service;

    public DiningHallController(DiningHallService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveFinishedOrder(@RequestBody FinishedOrder finishedOrder) {
        log.info("<-- Received " + finishedOrder.toString());
        service.receiveFinishedOrder(finishedOrder);
    }
}
