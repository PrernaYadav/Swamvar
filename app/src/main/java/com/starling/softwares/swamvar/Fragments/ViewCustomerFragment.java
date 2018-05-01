package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.ViewCustomerOrder;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.ViewCustomerModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCustomerFragment extends Fragment {
    @BindView(R.id.cust_recy)
    RecyclerView cust_recy;
    private String tag = ViewCustomerFragment.class.getSimpleName();
    private ViewCustomerOrder cust_recy_adapter;

    public ViewCustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_customer, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Select Customer");
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
        initRecycler();
        if (Helper.isDataConnected(getActivity())) {
            getDataFromServer();
        } else {
            Helper.showNoInternetToast(getActivity());
        }
    }

    private void getDataFromServer() {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "hold On");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        ServerConnection.requestServer(getString(R.string.get_customer), params, Constant.get_customer, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "view customer response >>>   " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        ViewCustomerModel viewCustomerModel = Helper.getGsonObject().fromJson(response, ViewCustomerModel.class);
                        if (!viewCustomerModel.getData().isEmpty()) {
                            cust_recy_adapter.setData(viewCustomerModel.getData());
                        }
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

    private void initRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cust_recy.setItemAnimator(new DefaultItemAnimator());
        cust_recy.setLayoutManager(manager);
        cust_recy_adapter = new ViewCustomerOrder(getActivity(), new ViewCustomerOrder.OnCustomerSelectListner() {
            @Override
            public void onSelected(ViewCustomerModel.DataBean model, int pos) {
                Home.getInstace().taketoOrdersById(model.getUser_id());
            }
        });
        cust_recy.setAdapter(cust_recy_adapter);
    }


}
