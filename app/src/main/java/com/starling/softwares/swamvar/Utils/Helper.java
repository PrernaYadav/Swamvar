package com.starling.softwares.swamvar.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starling.softwares.swamvar.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * Created by shobh on 7/5/2017.
 */

/**
 * helper class for frequently used methods .....................................
 */
public class Helper {


    public static void logcat(String tag, String msg) {
        Log.e(tag, msg);
    }


    /*public static int getimage(int size) {
        if (size == Constants.small) {
            return R.drawable.placeholder_small;
        } else if (size == Constants.potrait) {
            return R.drawable.placeholder_portrait;
        }

        return R.drawable.placeholder_big;
    }


    public static int getimageShuffle(int size) {
        if (size == Constants.odd) {
            return R.drawable.placeholder_dot;
        } else if (size == Constants.even) {
            return R.drawable.placeholder_black_background;
        }
        return R.drawable.placeholder_double_dot;
    }
*/

    public static Bitmap getImageBitmap(int image, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), image);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * check the data n wifi connection here....................................
     *
     * @param context
     * @return
     */
    public static boolean isDataConnected(Context context) {
        ConnectivityManager connectMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectMan.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isWiFiConnection(Context context) {
        ConnectivityManager connectMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectMan.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * show custom snackbar to the user
     *
     * @param msg
     * @param mContext
     * @param parent
     * @return
     */
    public static Snackbar showBar(String msg, Context mContext, View parent, int duration) {
        Snackbar mSnackbar = Snackbar.make(parent, msg, duration);
        View sbView = mSnackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        textView.setGravity(Gravity.CENTER);
        return mSnackbar;
    }


    public static void changeStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public static ProgressDialog showProgressDialog(Activity activity, String msg) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        return dialog;
    }


    public static void dismissDialog(ProgressDialog dialog) {
        if (dialog == null) return;
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /*public static RequestBody createPartFromString(String body) {
        return RequestBody.create(MultipartBody.FORM, body);
    }*/

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String msg, int Length) {
        Toast toast = Toast.makeText(context, msg, Length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static Gson getGsonObject() {
        return new GsonBuilder().setLenient().create();
    }


    /**
     * check if the gps is on or off
     *
     * @param context
     * @return
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    /**
     * get the human visible date from database format
     *
     * @param myDate
     * @return
     */
    public static String getDate(String myDate) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        String finalString = newFormat.format(date);
        return finalString;
    }


    public static Date converStringToDate(String dateconvert) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(dateconvert);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void GetYearMonthDat(String date, DateGetListner listner) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date parse = null;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        listner.getDate(c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
    }

    public static void showNoInternetToast(Context context) {
        showToast(context, "No internet available");
    }

    public static void showNoResponse(Context applicationContext) {
        showToast(applicationContext, "Unable to connect server");
    }

    public interface DateGetListner {
        void getDate(int date, int month, int year);
    }


    public static SharedPreferences getPreference(Context context) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference;
    }

}