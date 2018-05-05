package com.starling.softwares.swamvar.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.ShowAppointmentAdapter;
import com.starling.softwares.swamvar.Model.ShowAppointment;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAppointmentFragment extends Fragment {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<ShowAppointment> arrayList;
    ShowAppointmentAdapter showAppointmentAdapter;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showappointment, container, false);
        recyclerView = view.findViewById(R.id.recy_show_appo);
        textView = view.findViewById(R.id.textview);
        getAppointment();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showAppointmentAdapter = new ShowAppointmentAdapter(arrayList, getActivity());
        recyclerView.setAdapter(showAppointmentAdapter);
        return view;
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

    public void getAppointment() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        if (response.contains("success")) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);

                                JSONArray routearray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < routearray.length(); i++) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.GONE);

                                    String id = routearray.getJSONObject(i).getString("id");
                                    String subject = routearray.getJSONObject(i).getString("subject");
                                    String date = routearray.getJSONObject(i).getString("date");
                                    String status = routearray.getJSONObject(i).getString("status");
                                    String status_comment = routearray.getJSONObject(i).getString("status_comment");
                                    String time = routearray.getJSONObject(i).getString("time");
                                    String address = routearray.getJSONObject(i).getString("address");
                                    String email = routearray.getJSONObject(i).getString("email");
                                    String role_name = routearray.getJSONObject(i).getString("role_name");


                                    ShowAppointment showAppointment = new ShowAppointment();
                                    showAppointment.setId(id);
                                    showAppointment.setSubject(subject);
                                    showAppointment.setDate(date);
                                    showAppointment.setStatus(status);
                                    showAppointment.setStatus_comment(status_comment);
                                    showAppointment.setTime(time);
                                    showAppointment.setAddress(address);
                                    showAppointment.setEmail(email);
                                    showAppointment.setRole_name(role_name);

                                    arrayList.add(showAppointment);
                                }
                                showAppointmentAdapter.notifyDataSetChanged();
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Slow internet,Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", Constant.access_token);
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
}
