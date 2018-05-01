package com.starling.softwares.swamvar.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starling.softwares.swamvar.Model.User;

/**
 * Created by Admin on 1/3/2018.
 */

public class Utils {

    /**
     * returns active user object
     *
     * @param context
     * @return
     */
    public static User getActiveUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userObject = preferences.getString("user", "");
        if (userObject.equalsIgnoreCase("")) {
            return null;
        } else {
            Gson gson = new GsonBuilder().create();
            User user = gson.fromJson(userObject, User.class);
            return user;
        }
    }

    public static void setUser(Context context, String CompanyLogin) {

        Log.e(" Active user now ", CompanyLogin);

        try {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user", CompanyLogin);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
