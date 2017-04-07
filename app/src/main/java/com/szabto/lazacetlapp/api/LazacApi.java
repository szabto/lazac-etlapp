package com.szabto.lazacetlapp.api;

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

    @GET("/szori/")
    Call<MenusResponse> getFood(@Query("id") int food_id);
}
