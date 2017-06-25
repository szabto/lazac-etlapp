package com.szabto.lazacetlapp.api.structures;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;

/**
 * Created by kubu on 4/5/2017.
 */

public class HeaderItem implements StickyHeader {
    private String value;

    public HeaderItem(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
