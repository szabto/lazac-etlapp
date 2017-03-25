package com.szabto.szorietlap.api;

import android.os.AsyncTask;
import android.util.Log;

import com.szabto.szorietlap.structures.ResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 3/11/17.
 */

public class Api extends AsyncTask<String, Void, JSONObject>  {
    private static final String TAG = Api.class.getSimpleName();

    private static final String baseUrl = "http://szabto.com/szori/index.php";

    ResponseHandler resp;

    public void getMenus(ResponseHandler rh, int start) {
        resp = rh;
        this.execute("?start="+ String.valueOf(start));
    }

    public void getMenu(ResponseHandler rh, String menuId) {
        resp = rh;
        this.execute("?action=getday&id="+menuId);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if( resp != null )
            resp.onComplete(jsonObject);
    }

    @Override
    protected JSONObject doInBackground(String... arg0) {
        HttpHandler sh = new HttpHandler();

        String jsonStr = sh.makeServiceCall(baseUrl+arg0[0]);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                return new JSONObject(jsonStr);
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }

        return null;
    }
}
