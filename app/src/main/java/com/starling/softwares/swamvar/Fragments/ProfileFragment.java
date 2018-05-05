package com.starling.softwares.swamvar.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Utils;
import com.starling.softwares.swamvar.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private String tag = ProfileFragment.class.getSimpleName();
    Button btnSave;
    TextView btnEditProfile;
    TextView btnEditImage;
    EditText etName, etMobile, etEmail, etAddress;
    CircleImageView profileImage;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    byte[] byteArray;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String namee, email, address, mobile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading ...");
        progressDialog.show();
        getProfileData();
        btnEditImage = view.findViewById(R.id.image_edit);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnSave = view.findViewById(R.id.btn_profile_save);
        etName = view.findViewById(R.id.et_profile_name);
        etMobile = view.findViewById(R.id.et_profile_mobile);
        etEmail = view.findViewById(R.id.et_profile_email);
        etAddress = view.findViewById(R.id.et_profile_address);
        profileImage = view.findViewById(R.id.image_profile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setVisibility(View.VISIBLE);
                etName.setEnabled(true);
                etName.setCursorVisible(true);
                etName.setTextColor(getResources().getColor(R.color.blue));
                etMobile.setEnabled(true);
                etMobile.setCursorVisible(true);
                etMobile.setTextColor(getResources().getColor(R.color.blue));
                etEmail.setEnabled(true);
                etEmail.setCursorVisible(true);
                etEmail.setTextColor(getResources().getColor(R.color.blue));
                etAddress.setEnabled(true);
                etAddress.setCursorVisible(true);
                etAddress.setTextColor(getResources().getColor(R.color.blue));
            }
        });
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namee = etName.getText().toString();
                email = etEmail.getText().toString();
                mobile = etMobile.getText().toString();
                address = etAddress.getText().toString();
                updateProfile();
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        profileImage.setImageBitmap(bitmap);
                        byteArray = byteArrayOutputStream.toByteArray();
//                        encode = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        new updateImage().execute();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_cart:
                Home.getInstace().takeToCart();
                return true;
            case R.id.action_home:
                Home.getInstace().takeToLandingPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getProfileData() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("success")) {
                            progressDialog.dismiss();
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray routearray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < routearray.length(); i++) {


                                    String name = routearray.getJSONObject(i).getString("name");
                                    String email = routearray.getJSONObject(i).getString("email");
                                    String mobile = routearray.getJSONObject(i).getString("mobile");
                                    String address = routearray.getJSONObject(i).getString("address");
                                    String image = routearray.getJSONObject(i).getString("image");


                                    etName.setText(name);
                                    etEmail.setText(email);
                                    etMobile.setText(mobile);
                                    etAddress.setText(address);

                                    if (image.equals("")) {
                                        profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
                                    } else {
                                        Picasso.get().load(image).into(profileImage);

                                    }
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Slow internet,Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int scoket = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(scoket, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    public void updateProfile() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("success")) {
                            progressDialog.dismiss();
                            btnSave.setVisibility(View.GONE);
                            etName.setTextColor(Color.WHITE);
                            etEmail.setTextColor(Color.WHITE);
                            etMobile.setTextColor(Color.WHITE);
                            etAddress.setTextColor(Color.WHITE);
                            etName.setCursorVisible(false);
                            etEmail.setCursorVisible(false);
                            etMobile.setCursorVisible(false);
                            etAddress.setCursorVisible(false);
                            Toast.makeText(getActivity(), "Data saved successfully!" + response.toString(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Slow internet,Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
                params.put("name", namee);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("address", address);
                Log.v("save", "" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int scoket = 30000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(scoket, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
    }

    public class updateImage extends AsyncTask<String, Void, String> {
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            System.out.println("in asynctask");
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
            progressDialog.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            response = callService();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // TODO Auto-generated method stub
            super.onPostExecute(response);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            //Log.d("Response", "" + response);

            //responseCode
            JSONObject object;

            if (response != null) {
                try {
                    object = new JSONObject(response);

                    Log.d("object", "" + object);
                    if (response.contains("success")) {


                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();

            }
        }

        private String callService() {
            String url = Constant.URL_EDIT_IMAGE;
            HttpClient client = new HttpClient(url);
            try {


                client.connectForMultipart();
                client.addFormPart("user_id", Utils.getActiveUser(getActivity()).getUser_id());
                client.addFilePart("image", ".jpg", byteArray);

                //client.addFormPart("licenceIssuedate",effectdate);
                //client.addFormPart("licenceExpiredate",expiryDate);


                Log.d("client", client.toString());
                client.finishMultipart();
                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return response;
        }
    }
}
