package com.restaurant.DiningHall.service;

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

    public static void rateOrderBasedOnThePreparationTime(FinishedOrder finishedOrder){

        rateOrderBasedOnThePreparationTime(finishedOrder.getMaxWait(), finishedOrder.getServingTime().toEpochMilli(), finishedOrder.getPickUpTime());
    }

    public static void rateOrderBasedOnThePreparationTime(double maxWaitTime, Long servingTime, Long pickUpTime){
        int rating;
        long prepTime = servingTime - pickUpTime;
        maxWaitTime = maxWaitTime * TIME_UNIT;
        if(prepTime < maxWaitTime){
            rating = 5;
        } else if (prepTime < maxWaitTime * 1.1){
            rating = 4;
        } else if(prepTime < maxWaitTime * 1.2){
            rating = 3;
        } else if (prepTime < maxWaitTime * 1.3){
            rating = 2;
        } else if(prepTime < maxWaitTime * 1.4){
            rating = 1;
        } else {
            rating = 0;
        }
        average = totalRating.addAndGet(rating) / (double) numberOfOrdersServed.incrementAndGet();
        log.info("Average restaurant rating is :" + average);
    }

//    public static SubOrderRatingResponse submitExternalRating(SubOrderRatingRequest subOrderRatingRequest) {
//        return subOrderRatingResponse;
//    }
}