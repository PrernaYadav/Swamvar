package com.starling.softwares.swamvar.Interfaces;

import com.android.volley.VolleyError;

/**
 * Created by shobh on 7/10/2017.
 */

public interface serverConnectionListner {

    void onSuccess(String response);

    void onError(VolleyError error);

}
