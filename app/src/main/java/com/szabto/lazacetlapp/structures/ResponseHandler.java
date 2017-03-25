package com.szabto.lazacetlapp.structures;

import org.json.JSONObject;

/**
 * Created by root on 3/24/17.
 */

public interface ResponseHandler {
    void onComplete(JSONObject resp);
}
