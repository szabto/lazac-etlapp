package com.szabto.lazacetlapp.api.structures;

import com.szabto.lazacetlapp.helpers.FavoriteHelper;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public class FoodItem {
    private int id;
    private String name;
    private int price_high;
    private int price_low;
    private boolean can_favorited;
    private boolean is_loading = false;

    public boolean getCanfavorited() {
        return can_favorited;
    }

    public void setCanFavorited(boolean can_favorited) {
        this.can_favorited = can_favorited;
    }

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

    public void setIsFavorite(boolean fav) {
        if (fav) {
            FavoriteHelper.getInstance().addToFavorites(this.getId());
        } else {
            FavoriteHelper.getInstance().removeFromFavorites(this.getId());
        }
    }

    public boolean getIsFavorite() {
        return FavoriteHelper.getInstance().isFavorited(this.getId());
    }

    public boolean getLoading() {
        return this.is_loading;
    }

    public void setLoading(boolean load) {
        this.is_loading = load;
    }
}
