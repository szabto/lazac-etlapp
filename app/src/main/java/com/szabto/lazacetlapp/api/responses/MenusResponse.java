package com.szabto.lazacetlapp.api.responses;


import com.szabto.lazacetlapp.api.structures.MenuItem;

import java.util.List;

/**
 * Created by kubu on 4/4/2017.
 */

public class MenusResponse extends ResponseBase {
    private boolean there_more;
    private List<MenuItem> list;

    public List<MenuItem> getList() {
        return list;
    }

    public void setList(List<MenuItem> list) {
        this.list = list;
    }

    public boolean isThereMore() {
        return there_more;
    }

    public void setThereMore(boolean there_more) {
        this.there_more = there_more;
    }
}
