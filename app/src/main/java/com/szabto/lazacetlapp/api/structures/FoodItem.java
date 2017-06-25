package com.szabto.lazacetlapp.api.structures;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class FoodItem {
    private int id;
    private String name;
    private int price_high;
    private int price_low;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceHigh() {
        return price_high;
    }

    public void setPriceHigh(int price_high) {
        this.price_high = price_high;
    }

    public int getPriceLow() {
        return price_low;
    }

    public void setPriceLow(int price_low) {
        this.price_low = price_low;
    }
}
