package com.restaurant.DiningHall.service;

import com.restaurant.DiningHall.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ClientOrderService {
    private static final Map<Integer, FinishedClientOrder> finishedOrders = new HashMap<>();

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String FOOD_SERVICE_REGISTRATION_URL = "/register";

    private static final String CHECK_ORDER_URL = "/order/";
    @Value("${food-ordering-service.url}")
    private String foodOrderServiceUrl;

    @Value("${restaurant.address}")
    private String restaurantAddress;

    @Value("${restaurant.name}")
    private String restaurantName;

    @Value("${restaurant.id}")
    private Integer restaurantId;

    @Value("${kitchen.service.url}")
    private String kitchenServiceUrl;

    @PostConstruct
    public void registerRestaurant(){
        if(foodOrderServiceUrl != null) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(restaurantId);
            restaurant.setAddress(restaurantAddress);
            restaurant.setMenu(DiningHallService.foodList);
            restaurant.setMenuItems(DiningHallService.foodList.size());
            restaurant.setRating(OrderRatingService.average);
            restaurant.setName(restaurantName);
            restTemplate.postForEntity(foodOrderServiceUrl + FOOD_SERVICE_REGISTRATION_URL, restaurant, Void.class);
        }
    }

    public ClientSubOrderResponse sendClientOrderToKitchen(ClientSubOrderRequest clientSubOrderRequest) {
        Order order = new Order(clientSubOrderRequest.getItems());
        order.setMaxWait(clientSubOrderRequest.getMaximumWaitTime());
        order.setPickUpTime(Instant.now().toEpochMilli());
        log.info("<--- Sending new order to kitchen : "+order);
        Long registeredTime = Instant.now().toEpochMilli();

        FinishedClientOrder finishedClientOrder = new FinishedClientOrder();
        finishedClientOrder.setOrderId(order.getOrderId());
        finishedClientOrder.setRegisteredTime(registeredTime);
        finishedClientOrder.setCreatedTime(clientSubOrderRequest.getCreatedTime());
        finishedOrders.put(order.getOrderId(), finishedClientOrder);

        restTemplate.postForEntity(kitchenServiceUrl + "/order", order, Void.class);

        Double estimatedPrepTime = getEstimatedCookingTimeFromKitchen(order.getOrderId());

        finishedClientOrder.setEstimatedWaitingTime(estimatedPrepTime);

        ClientSubOrderResponse clientSubOrderResponse = new ClientSubOrderResponse();
        clientSubOrderResponse.setOrderId(order.getOrderId());
        clientSubOrderResponse.setEstimatedWaitingTime(estimatedPrepTime);
        clientSubOrderResponse.setRestaurantId(clientSubOrderRequest.getRestaurantId());
        clientSubOrderResponse.setRegisteredTime(registeredTime);
        clientSubOrderResponse.setCreatedTime(clientSubOrderRequest.getCreatedTime());
        //log.info("Suborder response : "+ clientSubOrderResponse + " for order : "+order+" finishedOrdersMap : "+finishedOrders);

        return clientSubOrderResponse;
    }

    public void receiveExternalOrder(FinishedOrder finishedOrder) {
        log.info("---> Received client order from Kitchen: "+finishedOrder+" finishedOrdersMap : "+finishedOrders);
        if(finishedOrder != null) {
            FinishedClientOrder finishedClientOrder = finishedOrders.get(finishedOrder.getOrderId());
            finishedClientOrder.setCookingTime(finishedOrder.getCookingTime());
            finishedClientOrder.setPreparedTime(Instant.now().toEpochMilli());
            finishedClientOrder.setCookingDetails(finishedOrder.getCookingDetails());
            finishedClientOrder.setMaximumWaitTime(finishedOrder.getMaxWait());
            finishedClientOrder.setEstimatedWaitingTime(null);
            finishedClientOrder.setIsReady(true);
        }
    }

    public FinishedClientOrder checkIfOrderIsReady(Integer id) {
        //log.info("Requesting status for finished order with id : "+id);
        FinishedClientOrder finishedClientOrder = finishedOrders.get(id);
        if (finishedClientOrder != null && finishedClientOrder.getIsReady()) {
            //log.info("Removing finished order with id : "+id);
            finishedOrders.remove(id);
        } else {
            finishedClientOrder.setEstimatedWaitingTime(getEstimatedCookingTimeFromKitchen(id));
        }

        //log.info("Returning order status : "+ finishedClientOrder);
        return finishedClientOrder;
    }

    private Double getEstimatedCookingTimeFromKitchen(Integer orderId) {
        ResponseEntity<Double> response = restTemplate.getForEntity(kitchenServiceUrl + CHECK_ORDER_URL + orderId, Double.class);

        return response.getBody();
    }
}
