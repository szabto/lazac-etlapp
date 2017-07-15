package com.szabto.lazacetlapp.api.responses;

import com.szabto.lazacetlapp.api.structures.DetailedFoodItem;
import com.szabto.lazacetlapp.api.structures.FoodStatisticWrapper;

/**
 * Created by kubu on 4/8/2017.
 */

public class FoodResponse extends ResponseBase {
    private DetailedFoodItem details;
    private FoodStatisticWrapper statistic;

    public DetailedFoodItem getDetails() {
        return details;
    }

    public void setDetails(DetailedFoodItem details) {
        this.details = details;
    }

    public FoodStatisticWrapper getStatistic() {
        return statistic;
    }

    public void setStatistic(FoodStatisticWrapper statistic) {
        this.statistic = statistic;
    }
}
