package com.szabto.lazacetlapp.api.responses;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kubu on 4/4/2017.
 */

public class ResponseBase {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
