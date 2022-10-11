package com.restaurant.DiningHall.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.DiningHall.constants.State;
import com.restaurant.DiningHall.models.FinishedOrder;
import com.restaurant.DiningHall.models.Food;
import com.restaurant.DiningHall.models.Order;
import com.restaurant.DiningHall.models.Table;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Slf4j
@Getter
public class DiningHallService {

    private static final Integer NUMBER_OF_TABLES = 10;
    private static final Integer NUMBER_OF_WAITERS = 5;

    private final static RestTemplate restTemplate = new RestTemplate();
    public static final List<Food> foodList = loadDefaultMenu();

    private static final List<Table> tables = new ArrayList<>();
    private final Map<Integer, ExecutorService> waiters = new HashMap<>();
    private int waiterIdCounter = 1;
    private final OrderRatingService orderRatingService;

    public static final int TIME_UNIT = 50;

    public DiningHallService(OrderRatingService orderRatingService) {
        initWaiters();
        initTables();
        this.orderRatingService = orderRatingService;
        tableReadyToMakeNewOrder();
    }

    @Scheduled(fixedRate = (long) (0.5*TIME_UNIT))
    public void tableReadyToMakeNewOrder() {
        tables.stream()
                .filter(table -> table.getState().equals(State.FREE))
                .findAny()
                .ifPresent(table -> {
                    table.setState(State.WAITING_TO_ORDER);
                    chooseWaiterForTable(table);
                });
    }

    private void chooseWaiterForTable(Table table) {
        Map.Entry<Integer, ExecutorService> waitersEntry = waiters.entrySet().stream()
                .min(Comparator.comparing(entry -> {
                    ThreadPoolExecutor tpe = (ThreadPoolExecutor) entry.getValue();
                    return tpe.getQueue().size();
                }))
                .orElseThrow(() -> new ArithmeticException("No Waiter is free"));

        waitersEntry.getValue().execute(() -> takeOrder(waitersEntry.getKey(), table));
    }

    private void takeOrder(int waiterId, Table table) {
        try {
            Thread.sleep(new Random().nextInt(3)+2 * TIME_UNIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Order order = table.generateOrder();

        order.setWaiterId(waiterId);
        table.setLastOrder(order);

        ResponseEntity<Void> orderResponse = restTemplate.postForEntity("http://localhost:8081/order", order, Void.class);
        if (orderResponse.getStatusCode().equals(HttpStatus.ACCEPTED)) {
            log.info("--> " + order + " was sent successfully.");
        } else {
            log.warn("<!!!!!> " + order + " was unsuccessful.");
        }
    }

    public void receiveFinishedOrder(FinishedOrder finishedOrder) {
        waiters.get(finishedOrder.getWaiterId()).execute(() -> bringFinishedOrderToTable(finishedOrder));
    }

    private void bringFinishedOrderToTable(FinishedOrder finishedOrder) {
        tables.get(finishedOrder.getTableId() - 1).verifyOrderRectitude(finishedOrder).setState(State.FREE);
        orderRatingService.rateOrderBasedOnThePreparationTime(finishedOrder);
    }

    private void initWaiters() {
        for (int i = 0; i < NUMBER_OF_WAITERS; i++) {
            waiters.put(waiterIdCounter++, Executors.newFixedThreadPool(1));
        }
    }

    private void initTables() {
        for (int i = 0; i < NUMBER_OF_TABLES; i++) {
            tables.add(new Table());
        }
    }

    private static List<Food> loadDefaultMenu() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = DiningHallService.class.getResourceAsStream("/menu-items.json");
        try {
            return mapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
