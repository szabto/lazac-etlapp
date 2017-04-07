package com.szabto.lazacetlapp.api;


import java.util.List;

/**
 * Created by kubu on 4/4/2017.
 */

public class MenusResponse extends ResponseBase {
    public List<MenuItem> getList() {
        return list;
    }

    public void setList(List<MenuItem> list) {
        this.list = list;
    }

    public boolean isThere_more() {
        return there_more;
    }

    public void setThere_more(boolean there_more) {
        this.there_more = there_more;
    }

    private boolean there_more;
    private List<MenuItem> list;
}
