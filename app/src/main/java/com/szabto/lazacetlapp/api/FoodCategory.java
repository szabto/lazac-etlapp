package com.szabto.lazacetlapp.api;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class FoodCategory {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }

    private String name;
    private List<FoodItem> items;
}
