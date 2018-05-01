package com.starling.softwares.swamvar.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressFragment extends Fragment {

    @BindView(R.id.city)
    AutoCompleteTextView city;

    @BindView(R.id.state)
    AutoCompleteTextView state;

    @BindView(R.id.add_address)
    Button add_address;

    @BindView(R.id.address)
    TextInputEditText address;
    @BindView(R.id.landmark)
    TextInputEditText landmark;
    @BindView(R.id.pin)
    TextInputEditText pin;
    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.mobile)
    TextInputEditText mobile;
    private String tag = AddressFragment.class.getSimpleName();
    private List<String> stateName, state_id;
    private String state_idd = null;
    private List<String> city_list, city_id_list;
    private String city_id_string = null;

    public static AddressFragment newInstance(String param1) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString("array", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Add New Address");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
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


    private void initView() {
        initSpinner();
        initViewRest();
        getState();
    }

    private void initViewRest() {
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    private void validateForm() {
        if (city_id_string == null) {
            Helper.showToast(getActivity(), "Please enter your city");
            return;
        }
        if (state_idd == null) {
            Helper.showToast(getActivity(), "Please enter your state");
            return;
        }
        if (address.getText().toString().trim().isEmpty()) {
            Helper.showToast(getActivity(), "Please enter your address");
            return;
        }
        if (landmark.getText().toString().trim().isEmpty()) {
            Helper.showToast(getActivity(), "Please enter your landmark");
            return;
        }
        if (pin.getText().toString().trim().isEmpty() || pin.getText().toString().trim().length() != 6) {
            Helper.showToast(getActivity(), "Please enter valid pin code");
            return;
        }
        if (name.getText().toString().trim().isEmpty()) {
            Helper.showToast(getActivity(), "Please enter your name");
            return;
        }
        if (mobile.getText().toString().trim().isEmpty() || mobile.getText().toString().trim().length() != 10) {
            Helper.showToast(getActivity(), "Please enter your valid mobile");
            return;
        }

        if (Helper.isDataConnected(getActivity())) {
            sendDataToServer();
        } else Helper.showNoInternetToast(getActivity());

    }

    private void sendDataToServer() {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold On");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        params.put("name", name.getText().toString().trim());
        params.put("mobile", mobile.getText().toString().trim());
        params.put("address", address.getText().toString().trim());
        params.put("landmark", landmark.getText().toString().trim());
        params.put("pincode", pin.getText().toString().trim());
        params.put("state_id", state_idd);
        params.put("city_id", city_id_string);

        ServerConnection.requestServer(getString(R.string.set_user_address), params, Constant.update_quantity, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "submit address response >>>>   " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        Helper.showToast(getActivity(), new JSONObject(response).getString("msg"));
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Home.getInstace().takeToAddressPage(getArguments().getString("array"));
                    } else {
                        Helper.showToast(getActivity(), new JSONObject(response).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
            }
        });
    }

    private void getState() {
        stateName = new ArrayList<>();
        state_id = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        ServerConnection.requestServer(getString(R.string.get_state), params, Constant.get_state, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "get state response >>>>    " + response);
                try {
                    JSONArray array = new JSONObject(response).getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        try {
                            stateName.add(object.getString("title"));
                            state_id.add(object.getString("state_id"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stateName);
                    state.setAdapter(activityAdapter);

                    state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (stateName.contains(state.getText().toString())) {
                                Helper.logcat(tag, "found man found");
                                state_idd = state_id.get(stateName.indexOf(state.getText().toString()));
                                setCityList(state_idd);
                            }

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


    private void setCityList(String state_idd) {
        Helper.logcat(tag, "city setCityList >>  " + state_idd);
        final ProgressDialog dialog = Helper.showProgressDialog(getActivity(), "Loading Cities...");
        dialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("state id", state_idd);
        ServerConnection.requestServer(getString(R.string.get_cities_php), params, Constant.get_city, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "city response >>  " + response);
                parseCityResponse(response);
                Helper.dismissDialog(dialog);
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(dialog);

            }
        });
    }

    private void parseCityResponse(String response) {
        city_id_list = new ArrayList<>();
        city_list = new ArrayList<>();
        try {
            JSONArray array = new JSONObject(response).getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                city_list.add(object.getString("city_name"));
                city_id_list.add(object.getString("city_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, city_list);
        city.setAdapter(activityAdapter);
        city.setThreshold(1);
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (city_list.contains(city.getText().toString())) {
                    Helper.logcat(tag, "found man found");
                    city_id_string = city_id_list.get(city_list.indexOf(city.getText().toString()));
                }
            }
        });
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        city.setThreshold(1);
        city.setAdapter(arrayAdapter);
        state.setThreshold(1);
        state.setAdapter(arrayAdapter);
    }

    public void onBackPressed() {

    }


}
