package com.restaurant.DiningHall.models;

import java.util.ArrayList;
import java.util.List;

public class CookingDetails {
    private List<CookingDetail> cookingDetailList;

    public List<CookingDetail> getCookingDetailList() {
        return cookingDetailList;
    }

    public void addCookingDetail(CookingDetail cookingDetail) {
        this.cookingDetailList.add(cookingDetail);
    }

    public CookingDetails(List<CookingDetail> cookingDetailList) {
        this.cookingDetailList = cookingDetailList;
    }

    public CookingDetails() {
        this.cookingDetailList = new ArrayList<>();
    }

    public CookingDetail[] toArray() {
        return getCookingDetailList().toArray(new CookingDetail[0]);
    }
}
