package com.restaurant.DiningHall.models;

import com.restaurant.DiningHall.constants.State;
import com.restaurant.DiningHall.service.DiningHallService;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Table {

    private int id;
    private State state = State.FREE;
    private Order lastOrder;
    private static AtomicInteger idCounter = new AtomicInteger();

    public Table() {
        this.id = idCounter.incrementAndGet();
    }

    private int NR_OF_FOODS = 6;

    public Order generateOrder() {
        Random random = new Random();
        int numberOfFoods = random.nextInt(NR_OF_FOODS) + 1;
        List<Integer> itemsId = new ArrayList<>();
        List<Food> foodsList = DiningHallService.foodList;

        for (int i = 0; i < numberOfFoods; i++) {
            itemsId.add(foodsList.get(random.nextInt(foodsList.size())).getId());
        }

        double maxWait = itemsId.stream()
                .map(e -> foodsList.get(e - 1))
                .map(Food::getId)
                .max(Integer::compare)
                .orElseThrow(() -> new IllegalArgumentException("MaxWait is Negative."));

        int priority = random.nextInt(5) + 1;
        return new Order(this.id, itemsId, priority, maxWait * 1.3, Instant.now().toEpochMilli());
    }

    public Table verifyOrderRectitude(FinishedOrder finishedOrder) {
        if (!(finishedOrder.getOrderId() == lastOrder.getOrderId()
                && finishedOrder.getItems().equals(lastOrder.getItems()))) {
            throw new RuntimeException("The Order is wrong.");
        }
        return this;
    }
}
