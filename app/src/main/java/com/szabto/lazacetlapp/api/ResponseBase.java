package com.szabto.lazacetlapp.api;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kubu on 4/4/2017.
 */

public class ResponseBase {
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
}
