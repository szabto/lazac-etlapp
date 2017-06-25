package com.szabto.lazacetlapp.api;

import com.szabto.lazacetlapp.api.responses.BroadcastResponse;
import com.szabto.lazacetlapp.api.responses.DayResponse;
import com.szabto.lazacetlapp.api.responses.FoodResponse;
import com.szabto.lazacetlapp.api.responses.MenusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kubu on 4/4/2017.
 */

public interface LazacApi {
    @GET("/szori/")
    Call<MenusResponse> getMenuList(@Query("start") int start);

    @GET("/szori/?action=getday")
    Call<DayResponse> getDay(@Query("id") int day_id);

    @GET("/szori/?action=getfood")
    Call<FoodResponse> getFood(@Query("id") int food_id);

    @GET("/szori/?action=gettoday")
    Call<DayResponse> getToday();

    @GET("/szori/?action=getbroadcast")
    Call<BroadcastResponse> getBroadcast();
}
