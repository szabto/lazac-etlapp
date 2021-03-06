package com.szabto.lazacetlapp.api.structures;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class FoodCategory {
    private String name;
    private List<FoodItem> items;
    private boolean can_favorited;

    public boolean getCanfavorited() {
        return can_favorited;
    }

    public void setCanFavorited(boolean can_favorited) {
        this.can_favorited = can_favorited;
    }

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
}
