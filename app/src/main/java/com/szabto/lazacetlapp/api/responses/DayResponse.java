package com.szabto.lazacetlapp.api.responses;

import com.szabto.lazacetlapp.api.structures.FoodCategory;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class DayResponse extends ResponseBase {
    private String date;
    private List<FoodCategory> data;

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
}
