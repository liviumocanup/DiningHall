package com.restaurant.DiningHall.service;

import com.restaurant.DiningHall.models.ClientSubOrderRatingRequest;
import com.restaurant.DiningHall.models.ClientSubOrderRatingResponse;
import com.restaurant.DiningHall.models.FinishedOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.restaurant.DiningHall.service.DiningHallService.TIME_UNIT;

@Service
@Slf4j
public class OrderRatingService {

    private static final AtomicLong totalRating = new AtomicLong();
    private static final AtomicInteger numberOfOrdersServed = new AtomicInteger();
    public static double average = 0d;

    public static void rateOrderBasedOnThePreparationTime(FinishedOrder finishedOrder, String restaurantName) {

        rateOrderBasedOnThePreparationTime(finishedOrder.getMaxWait(), finishedOrder.getServingTime().toEpochMilli(), finishedOrder.getPickUpTime(), restaurantName);
    }

    public static void rateOrderBasedOnThePreparationTime(double maxWaitTime, Long servingTime, Long pickUpTime, String restaurantName) {
        int rating;
        long prepTime = servingTime - pickUpTime;
        maxWaitTime = maxWaitTime * TIME_UNIT;
        if (prepTime < maxWaitTime) {
            rating = 5;
        } else if (prepTime < maxWaitTime * 1.1) {
            rating = 4;
        } else if (prepTime < maxWaitTime * 1.2) {
            rating = 3;
        } else if (prepTime < maxWaitTime * 1.3) {
            rating = 2;
        } else if (prepTime < maxWaitTime * 1.4) {
            rating = 1;
        } else {
            rating = 0;
        }
        average = totalRating.addAndGet(rating) / (double) numberOfOrdersServed.incrementAndGet();
        if(restaurantName != null){
            log.info(restaurantName + ": average restaurant rating is :" + average);
        }else {
            log.info("Average rating is :" + average);
        }
    }

    public static ClientSubOrderRatingResponse submitExternalRating(ClientSubOrderRatingRequest subOrderRatingRequest) {
        ClientSubOrderRatingResponse subOrderRatingResponse = new ClientSubOrderRatingResponse();
        subOrderRatingResponse.setRestaurantId(subOrderRatingRequest.getRestaurantId());
        subOrderRatingResponse.setRestaurantAvgRating(average = totalRating.addAndGet(subOrderRatingRequest.getRating()) / (double) numberOfOrdersServed.incrementAndGet());
        subOrderRatingResponse.setPreparedOrders(numberOfOrdersServed.get());

        return subOrderRatingResponse;
    }
}