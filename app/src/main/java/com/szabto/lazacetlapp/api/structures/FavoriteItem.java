package com.szabto.lazacetlapp.api.structures;

/**
 * Created by kubu on 6/25/2017.
 */

public class FavoriteItem {
    private String name;
    private int id;

    private boolean loading;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
