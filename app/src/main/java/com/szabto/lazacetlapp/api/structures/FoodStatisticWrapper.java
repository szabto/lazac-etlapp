package com.szabto.lazacetlapp.api.structures;

import java.util.List;

/**
 * Created by szabto on 7/16/17.
 */

public class FoodStatisticWrapper {
    private List<String> occurenceDates;
    private int occurrence;
    private int served_count;

    public int getServedCount() {
        return served_count;
    }

    public void setServedCount(int served_count) {
        this.served_count = served_count;
    }

    public List<String> getOccurenceDates() {
        return occurenceDates;
    }

    public void setOccurenceDates(List<String> occurenceDates) {
        this.occurenceDates = occurenceDates;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }
}
