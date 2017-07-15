package com.szabto.lazacetlapp.api.structures;

import java.util.List;

/**
 * Created by kubu on 4/8/2017.
 */

public class DetailedFoodItem {
    private String name;
    private String image_url;
    private String description;
    private PriceWrapper prices;
    private FoodStatisticWrapper statistic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriceWrapper getPrices() {
        return prices;
    }

    public void setPrices(PriceWrapper prices) {
        this.prices = prices;
    }

    public class PriceWrapper {
        private int high_min;
        private int high_max;
        private int high_avg;

        private int low_min;
        private int low_max;
        private int low_avg;

        public int getHighMin() {
            return high_min;
        }

        public void setHighMin(int high_min) {
            this.high_min = high_min;
        }

        public int getHighMax() {
            return high_max;
        }

        public void setHighMax(int high_max) {
            this.high_max = high_max;
        }

        public int getHighAvg() {
            return high_avg;
        }

        public void setHighAvg(int high_avg) {
            this.high_avg = high_avg;
        }

        public int getLowMin() {
            return low_min;
        }

        public void setLowMin(int low_min) {
            this.low_min = low_min;
        }

        public int getLowMax() {
            return low_max;
        }

        public void setLowMax(int low_max) {
            this.low_max = low_max;
        }

        public int getLowAvg() {
            return low_avg;
        }

        public void setLowAvg(int low_avg) {
            this.low_avg = low_avg;
        }
    }
}
