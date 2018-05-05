package com.starling.softwares.swamvar;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.User;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.SessionManager;
import com.starling.softwares.swamvar.Utils.Utils;

import android.accounts.AccountAuthenticatorActivity;

import java.util.HashMap;

public class Login extends AccountAuthenticatorActivity {
    private static final String TAG = Login.class.getSimpleName();
    private static final int LOCATION_CODE = 101;
    private Button btnLogin;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String mOldAccountType;
    String password,email,deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        inputEmail =  findViewById(R.id.email);
        inputPassword =  findViewById(R.id.password);
        btnLogin =  findViewById(R.id.btnLogin);
        deviceId = FirebaseInstanceId.getInstance().getToken();
        Log.v("deviceid",""+ deviceId);


        session = new SessionManager(getApplicationContext());
        if (getIntent().hasExtra(AccountManager.KEY_ACCOUNT_TYPE)) {
            mOldAccountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
            Helper.logcat("IntentData is not null WelcomeActivity " + mOldAccountType, "");
            CreateSyncAccount();
        } else {
            Helper.logcat("IntentData is null WelcomeActivity ", "");
            /**
             * Checking if user already connected
             */
/*
            if (PreferenceManager.getToken(this) != null) {
                getAdsInformation();
                getInterstitialAdInformation();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }*/

        }


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            //    checkLogin();
                //  startActivity(new Intent(Login.this, Home.class));
                 password = inputPassword.getText().toString().trim();
                 email = inputEmail.getText().toString().trim();
                if (isLocationPermissionGranted()) {
                    MakeUserLogin(email, password);
                } else {
                    retquestPermission();
                }
            }
        });

    }

    /**
     * Create a new  account for the sync adapter
     */
    public Account CreateSyncAccount() {
        if (mOldAccountType != null) {
            // Create the account type and default account
            Account newAccount = new Account(getString(R.string.app_name), mOldAccountType);
            // Get an instance of the Android account manager
            AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);

            if (!accountManager.addAccountExplicitly(newAccount, null, null)) {
                Helper.logcat("account already exits", getString(R.string.app_name));
                finish();

            }
            return newAccount;
        } else {
            return null;
        }

    }


    private void MakeUserLogin(String email, String password) {
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !password.isEmpty()) {
            if (Helper.isDataConnected(getApplicationContext()))
                checkLogin(email,password);
            else Helper.showNoInternetToast(getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
        }
    }

    private void retquestPermission() {
        ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
    }

    private boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void checkLogin(String email,String password) {
        pDialog = Helper.showProgressDialog(Login.this, "Validating credentials");
        pDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", "cjhiZWFxT0szZ3ZQaUZqTElqMmJxQT09");
        params.put("email", email);
        params.put("password", password);
        params.put("device_id", deviceId);



        ServerConnection.requestServer("http://swayamvar.starlingsoftwares.in/appservices/login.php", params, Constant.login, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(pDialog);
                Log.v("response", response);
                Helper.logcat(TAG, "user login response >>>  " + response);
                User user = Helper.getGsonObject().fromJson(response, User.class);
                if (user != null) {
                    if (user.getStatus().equalsIgnoreCase("success")) {
                        String accountType = "com.starling.softwares.swamvar";
                        AccountManager accountManager = AccountManager.get(Login.this);

                        // This is the magic that add the account to the Android Account Manager
                        final Account account = new Account(getResources().getString(R.string.app_name), accountType);
                        accountManager.addAccountExplicitly(account, "9990193401", null);

                        // Now we tell our caller, could be the Android Account Manager or even our own application
                        // that the process was successful
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, getResources().getString(R.string.app_name));
                        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
                        setAccountAuthenticatorResult(intent.getExtras());
                        setResult(RESULT_OK, intent);
                        session.setLogin(true);

                        Utils.setUser(getApplicationContext(), response);

                        startActivity(intent);
                        finish();
                    } else {
                        Helper.showToast(getApplicationContext(), "Invalid Credentials");
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(pDialog);
                error.printStackTrace();
                Log.v("error",""+error.toString());
                Helper.showNoResponse(getApplicationContext());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                MakeUserLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
            } else {
                Helper.showToast(getApplicationContext(), "Please grant permission to move further");
            }
        }
    }
}
