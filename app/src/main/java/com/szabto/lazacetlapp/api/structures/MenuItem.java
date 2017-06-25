package com.szabto.lazacetlapp.api.structures;

/**
 * Created by kubu on 4/4/2017.
 */

public class MenuItem {
    private String day_name;
    private int id;
    private int week_num;
    private String date;
    private String posted;
    private int item_count;

    public String getDayName() {
        return day_name;
    }

    public void setDayName(String day_name) {
        this.day_name = day_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekNum() {
        return week_num;
    }

    public void setWeekNum(int week_num) {
        this.week_num = week_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public int getItemCount() {
        return item_count;
    }

    public void setItemCount(int item_count) {
        this.item_count = item_count;
    }
}
