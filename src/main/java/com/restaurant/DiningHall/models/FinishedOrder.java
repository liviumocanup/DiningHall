package com.restaurant.DiningHall.models;

import java.util.Arrays;

public class FinishedOrder {
    private Long order_id;
    private Long table_id;
    private Long waiter_id;
    private Long[] items;
    private int priority;
    private Double max_wait;
    private Long pick_up_time;
    private Double cooking_time;
    private CookingDetails cooking_details;

    @Override
    public String toString() {
        return "FinishedOrder{" +
                "order_id=" + order_id +
                ", table_id=" + table_id +
                ", waiter_id=" + waiter_id +
                ", items=" + Arrays.toString(items) +
                ", priority=" + priority +
                ", max_wait=" + max_wait +
                ", pick_up_time=" + pick_up_time +
                ", cooking_time=" + cooking_time +
                ", cooking_details=" + Arrays.toString(cooking_details.toArray()) +
                '}';
    }

    public FinishedOrder(Long orderId, Long tableId, Long waiterId, Long[] items, int priority, Double maxWait, Long pickUpTime, Double cookingTime, CookingDetails cookingDetails) {
        this.order_id = orderId;
        this.table_id = tableId;
        this.waiter_id = waiterId;
        this.items = items;
        this.priority = priority;
        this.max_wait = maxWait;
        this.pick_up_time = pickUpTime;
        this.cooking_time = cookingTime;
        this.cooking_details = cookingDetails;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getTable_id() {
        return table_id;
    }

    public void setTable_id(Long table_id) {
        this.table_id = table_id;
    }

    public Long getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(Long waiter_id) {
        this.waiter_id = waiter_id;
    }

    public Long[] getItems() {
        return items;
    }

    public void setItems(Long[] items) {
        this.items = items;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Double getMax_wait() {
        return max_wait;
    }

    public void setMax_wait(Double max_wait) {
        this.max_wait = max_wait;
    }

    public Long getPick_up_time() {
        return pick_up_time;
    }

    public void setPick_up_time(Long pick_up_time) {
        this.pick_up_time = pick_up_time;
    }

    public Double getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(Double cooking_time) {
        this.cooking_time = cooking_time;
    }

    public CookingDetails getCooking_details() {
        return cooking_details;
    }

    public void setCooking_details(CookingDetails cooking_details) {
        this.cooking_details = cooking_details;
    }
}
