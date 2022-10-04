package com.restaurant.DiningHall.models;

public class CookingDetail {
    private Long foodId;
    private Long cookId;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getCookId() {
        return cookId;
    }

    public void setCookId(Long cookId) {
        this.cookId = cookId;
    }

    public CookingDetail(Long foodId, Long cookId) {
        this.foodId = foodId;
        this.cookId = cookId;
    }

    @Override
    public String toString() {
        return '{' +
                "foodId=" + foodId +
                ", cookId=" + cookId +
                '}';
    }
}
