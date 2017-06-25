package com.szabto.lazacetlapp.helpers;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kubu on 6/25/2017.
 */

public class FavoriteHelper {
    private static FavoriteHelper instance = null;

    public static FavoriteHelper getInstance() {
        if( instance == null ) instance = new FavoriteHelper();
        return instance;
    }

    private String userToken = null;
    private final List<Integer> favorites = new ArrayList<Integer>();
    private final Object favoritesLock = new Object();

    private FavoriteHelper() {}

    public void setUserToken(String token) {
        this.userToken = token;
    }

    public String getUserToken() {
        return this.userToken;
    }

    public boolean isFavorited( int foodId ) {
        boolean response = false;
        synchronized (favoritesLock) {
            if( favorites.contains(foodId) ) {
                response = true;
            }
        }
        return response;
    }

    public void addToFavorites( int foodId ) {
        synchronized (favoritesLock) {
            if( !favorites.contains(foodId) ) {
                favorites.add(foodId);
            }
        }
    }

    public void removeFromFavorites( int foodId ) {
        synchronized (favoritesLock) {
            if( favorites.contains(foodId) ) {
                favorites.remove((Object) foodId);
            }
        }
    }

    public Integer[] getFavorites() {
        Integer[] result = null;
        synchronized (favoritesLock) {
            result = favorites.toArray(new Integer[]{});
        }
        return result;
    }
}
