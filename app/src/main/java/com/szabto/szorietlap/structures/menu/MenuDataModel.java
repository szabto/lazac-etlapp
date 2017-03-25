package com.szabto.szorietlap.structures.menu;

/**
 * Created by root on 3/24/17.
 */

public class MenuDataModel {
    String valid;
    Boolean isNew;
    String posted;
    Integer itemCount;
    Integer id;

    public MenuDataModel( Integer id, String valid, String posted, Integer itemCount, Boolean isNew) {
        this.id = id;
        this.valid = valid;
        this.isNew = isNew;
        this.itemCount = itemCount;
        this.posted = posted;
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