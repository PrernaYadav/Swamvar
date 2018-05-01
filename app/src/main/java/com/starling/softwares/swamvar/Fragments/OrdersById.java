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
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.OrdersByIdAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.OrdersByIdModel;
import com.starling.softwares.swamvar.Model.ProductDescriptionModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersById extends Fragment {
    @BindView(R.id.order_recy)
    RecyclerView order_recy;
    @BindView(R.id.cart_empty)
    LinearLayout cart_empty;
    private OrdersByIdAdapter orderByIdAdapter;
    private String tag = OrdersById.class.getSimpleName();

    public OrdersById() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Orders");

    }

    public static OrdersById newInstance(String user_id) {
        OrdersById ordersById = new OrdersById();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id);
        ordersById.setArguments(bundle);
        return ordersById;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_by_id, container, false);
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
        initRecycler();
        if (Helper.isDataConnected(getActivity())) getDatafromserver();
        else Helper.showNoInternetToast(getActivity());
    }

    private void getDatafromserver() {
//        Helper.logcat(tag,"getDatafromserver user_id >>  "+user_id);
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Fetching Data");
        progressDialog.show();
        final HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", getArguments().getString("user_id"));
        ServerConnection.requestServer(getString(R.string.get_orders), params, Constant.get_design_details, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "product information  >>  " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        OrdersByIdModel ordersByIdModel = Helper.getGsonObject().fromJson(response, OrdersByIdModel.class);
                        if (!ordersByIdModel.getData().isEmpty()) {
                            orderByIdAdapter.setData(ordersByIdModel.getData());
                        } else {
                            order_recy.setVisibility(View.GONE);
                            cart_empty.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
                error.printStackTrace();
            }
        });
    }

    private void initRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        order_recy.setItemAnimator(new DefaultItemAnimator());
        order_recy.setLayoutManager(manager);
        orderByIdAdapter = new OrdersByIdAdapter(getActivity(), new OrdersByIdAdapter.OnOrderviewListner() {
            @Override
            public void onView(OrdersByIdModel.DataBean model, int position) {
                Home.getInstace().takeToProductOrdersDetails(model);
            }
        });
        order_recy.setAdapter(orderByIdAdapter);
    }


}
