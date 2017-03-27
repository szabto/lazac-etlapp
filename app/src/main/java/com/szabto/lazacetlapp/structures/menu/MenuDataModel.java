package com.szabto.lazacetlapp.structures.menu;

/**
 * Created by root on 3/24/17.
 */

public class MenuDataModel {
    String valid;
    Boolean isNew;
    String posted;
    Integer itemCount;
    Integer id;

    String dayName;

    boolean isHeader;

    public MenuDataModel( Integer id, boolean isHeader, String valid, String posted, Integer itemCount, String dayName, Boolean isNew) {
        this.id = id;
        this.valid = valid;
        this.isNew = isNew;
        this.isHeader = isHeader;
        this.itemCount = itemCount;
        this.posted = posted;
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public boolean isHeader() {
        return isHeader;
    }
    public void setHeader(boolean header) {
        isHeader = header;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}