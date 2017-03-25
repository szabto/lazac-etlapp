package com.szabto.szorietlap.structures.item;

/**
 * Created by root on 3/24/17.
 */

public class ItemDataModel {
    String name;
    int price_low;
    int price_high;
    boolean isCategory;

    public ItemDataModel(String name, boolean isCategory, int priceh, int pricel) {
        this.name = name;
        this.isCategory = isCategory;
        this.price_low = pricel;
        this.price_high = priceh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice_low() {
        return price_low;
    }

    public void setPrice_low(int price_low) {
        this.price_low = price_low;
    }

    public int getPrice_high() {
        return price_high;
    }

    public void setPrice_high(int price_high) {
        this.price_high = price_high;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }
}