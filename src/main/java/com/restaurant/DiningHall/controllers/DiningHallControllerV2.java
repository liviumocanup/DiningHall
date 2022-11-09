package com.restaurant.DiningHall.controllers;

import com.restaurant.DiningHall.models.*;
import com.restaurant.DiningHall.service.ClientOrderService;
import com.restaurant.DiningHall.service.OrderRatingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2")
public class DiningHallControllerV2 {
    private final ClientOrderService clientOrderService;

    public DiningHallControllerV2(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping("/order/{id}")
    public FinishedClientOrder checkIfOrderIsReady(@PathVariable Integer id) {
        return clientOrderService.checkIfOrderIsReady(id);
    }

    @PostMapping("/order")
    public ClientSubOrderResponse submitExternalOrder(@RequestBody ClientSubOrderRequest clientSubOrderRequest) {
        return clientOrderService.sendClientOrderToKitchen(clientSubOrderRequest);
    }

    @PostMapping("/rating")
    public ClientSubOrderRatingResponse submitRating(@RequestBody ClientSubOrderRatingRequest subOrderRatingRequest) {
        return OrderRatingService.submitExternalRating(subOrderRatingRequest);
    }
}
