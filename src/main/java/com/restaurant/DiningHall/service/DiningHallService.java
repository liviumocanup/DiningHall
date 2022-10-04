package com.restaurant.DiningHall.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.DiningHall.models.Food;
import com.restaurant.DiningHall.models.Menu;
import com.restaurant.DiningHall.models.Order;
import com.restaurant.DiningHall.models.Table;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

@Service
public class DiningHallService {

    static RestTemplate restTemplate = new RestTemplate();
    private static final List<Food> foodList = loadDefaultMenu();


    public DiningHallService(){
        AtomicLong i = new AtomicLong();
        AtomicLong j = new AtomicLong();
        Runnable runnable = () -> {
            for (int k = 0; k < 3; k++) {
                int timeToWait = new Random().nextInt(4000) + 4000;
                try {
                    sleep(timeToWait);

                    System.out.println(timeToWait);

                    j.compareAndSet(10, 0);
                    Order order = new Table().generateOrder(i.incrementAndGet(), j.incrementAndGet(), foodList);

                    String t = restTemplate.postForObject("http://kitchen-docker:8081/order", order, String.class);
                    System.out.println(order);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        Thread thread3 = new Thread(runnable);
        thread3.start();
    }

    private static List<Food> loadDefaultMenu(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = DiningHallService.class.getResourceAsStream("/menu-items.json");
        try {
            return mapper.readValue(is, new TypeReference<List<Food>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
