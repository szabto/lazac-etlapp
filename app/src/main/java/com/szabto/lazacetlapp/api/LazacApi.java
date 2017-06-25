package com.szabto.lazacetlapp.api;

import com.szabto.lazacetlapp.api.responses.BroadcastResponse;
import com.szabto.lazacetlapp.api.responses.DayResponse;
import com.szabto.lazacetlapp.api.responses.FoodResponse;
import com.szabto.lazacetlapp.api.responses.MenusResponse;
import com.szabto.lazacetlapp.api.responses.ResponseBase;
import com.szabto.lazacetlapp.api.structures.FavoriteItem;

import java.util.List;

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

    @GET("/szori/?action=setfavoritestate")
    Call<ResponseBase> setFavoriteState(@Query("id") int food_id, @Query("uid") String user_token, @Query("state") boolean state);

    @GET("/szori/?action=getfavoritedfoods")
    Call<List<FavoriteItem>> getFavoritedFoods(@Query("uid") String user_token);

    @GET("/szori/?action=registeruuid")
    Call<ResponseBase> registerUUID(@Query("uuid") String uuid, @Query("firebase_id") String firebase_id);
}
