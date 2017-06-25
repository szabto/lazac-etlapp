package com.szabto.lazacetlapp.api.responses;

import com.szabto.lazacetlapp.api.structures.DetailedFoodItem;

/**
 * Created by kubu on 4/8/2017.
 */

public class FoodResponse extends ResponseBase {
    private DetailedFoodItem details;

    public DetailedFoodItem getDetails() {
        return details;
    }

    public void setDetails(DetailedFoodItem details) {
        this.details = details;
    }
}
