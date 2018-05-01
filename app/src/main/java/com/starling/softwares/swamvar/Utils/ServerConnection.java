package com.starling.softwares.swamvar.Utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by shobh on 7/10/2017.
 */

public class ServerConnection {


    public static void requestServer(String url, final HashMap<String, String> params, String constTag, final serverConnectionListner listner) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listner.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listner.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setRetryPolicy(MyApplication.getDefaultRetryPolice());
        MyApplication.getInstance().addToRequestQueue(request, constTag);
    }


}
