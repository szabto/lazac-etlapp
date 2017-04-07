package com.szabto.lazacetlapp.api;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class DayResponse extends ResponseBase {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FoodCategory> getData() {
        return data;
    }

    public void setData(List<FoodCategory> data) {
        this.data = data;
    }

    private List<FoodCategory> data;
}
