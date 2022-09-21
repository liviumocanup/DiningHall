package com.restaurant.DiningHall.models;

import com.restaurant.DiningHall.constants.State;

import java.util.List;
import java.util.Random;

public class Table {
    private State state = State.FREE;
    //private static
    private static int maxItemsPerOrder = 10;

    public State getState() {
        return state;
    }

    public static void setMaxItemsPerOrder(int maxItemsPerOrder) {
        Table.maxItemsPerOrder = maxItemsPerOrder;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Table() {
    }

    public Order generateOrder(Long orderId, Long tableId, List<Food> foodList) {
        Random rn = new Random();
        Long[] items = new Long[rn.nextInt(maxItemsPerOrder) + 1];
        double maxWait = -1;

        for (int i = 0; i < items.length; i++) {
            //choose random id from available IDs
            items[i] = getAvailableIds(foodList)[rn.nextInt(getAvailableIds(foodList).length)];
            maxWait = Math.max(maxWait, foodList.get((int) (items[i] - 1)).getPreparation_time());
        }

        if (maxWait == -1)
            System.out.println("ERROR");
        return new Order(orderId, tableId, (long) rn.nextInt(3) + 1, items, rn.nextInt(5) + 1, maxWait, System.nanoTime());
    }

    public static Long[] getAvailableIds(List<Food> foodList){
        Long[] ids = new Long[foodList.size()];
        for(int i=0; i<ids.length; i++){
            ids[i] = foodList.get(i).getId();
        }
        return ids;
    }
}
