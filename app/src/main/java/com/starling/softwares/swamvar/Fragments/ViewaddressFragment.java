package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.AddressAdapter;
import com.starling.softwares.swamvar.Adapter.CartAdapter;
import com.starling.softwares.swamvar.Adapter.ViewCustomerOrder;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.AddressModel;
import com.starling.softwares.swamvar.Model.CartModel;
import com.starling.softwares.swamvar.Model.ViewCustomerModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewaddressFragment extends Fragment {
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.total_items)
    TextView total_items;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.new_address)
    RelativeLayout new_address;
    @BindView(R.id.address_recy)
    RecyclerView address_recy;
    @BindView(R.id.peice)
    TextView peice;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.myself)
    TextView myself;
    @BindView(R.id.customer)
    TextView customer;
    private AddressAdapter address_adapter;
    private String address_string = null;
    private String tag = ViewaddressFragment.class.getSimpleName();
    @BindView(R.id.cust_recy)
    RecyclerView cust_recy;
    @BindView(R.id.add_customer)
    TextView add_customer;
    private ViewCustomerOrder cust_recy_adapter;
    private String order_for = null;

    private String user_id = null;

    public ViewaddressFragment() {
        // Required empty public constructor
    }

    public static ViewaddressFragment newInstance(String param1) {
        ViewaddressFragment fragment = new ViewaddressFragment();
        Bundle args = new Bundle();
        args.putString("array", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Select Address");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewaddress, container, false);
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
        if (Helper.isDataConnected(getActivity())) {
            getAddress();
            getDataFromServer();
        } else Helper.showNoInternetToast(getActivity());

        try {
            JSONObject object = new JSONObject(getArguments().getString("array"));
            total_price.setText(String.format("Amount :₹ %s", object.getString("ttl_amount")));
            total_items.setText(String.format("%s items", String.valueOf(object.getJSONArray("products").length())));
            peice.setText(String.format("Price (%s products)", String.valueOf(object.getJSONArray("products").length())));
            cost.setText(String.format("₹ %s", object.getString("ttl_amount")));
            total.setText(String.format("Amount Payable : ₹%s", object.getString("ttl_amount")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address_string == null) {
                    Helper.showToast(getActivity(), "Please select an address");
                    return;
                }
                if (user_id == null) {
                    Helper.showToast(getActivity(), "Please select for whom order is getting placed");
                    return;
                }
                try {
                    JSONObject object = new JSONObject(getArguments().getString("array"));
                    object.put("shipping_address", address_string);
                    object.put("user_id", user_id);
                    Helper.logcat(tag, "final json >>>   " + object.toString());
                    submitOrder(object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.getInstace().takeToAddressAddPage(getArguments().getString("array"));
            }
        });

        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_for = "myself";
                myself.setBackground(ContextCompat.getDrawable(getActivity(), android.R.drawable.editbox_background_normal));
                customer.setBackground(ContextCompat.getDrawable(getActivity(), android.R.drawable.edit_text));
                cust_recy.setVisibility(View.GONE);
                user_id = Utils.getActiveUser(getActivity()).getUser_id();
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_for = "customer";
                myself.setBackground(ContextCompat.getDrawable(getActivity(), android.R.drawable.edit_text));
                customer.setBackground(ContextCompat.getDrawable(getActivity(), android.R.drawable.editbox_background_normal));
                if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("1")) {
                    add_customer.setVisibility(View.VISIBLE);
                    add_customer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                cust_recy.setVisibility(View.VISIBLE);
                user_id = null;
            }
        });
    }

    private void submitOrder(String s) {

        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold on");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        params.put("order_details", s);
        ServerConnection.requestServer(getString(R.string.set_order), params, Constant.delete_cart_item, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "get address   >>>   " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        Helper.showToast(getActivity(), new JSONObject(response).getString("msg"));
                        Home.getInstace().takeToLandingPage();
                    } else
                        Helper.showToast(getActivity(), new JSONObject(response).getString("msg"));
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

    private void getAddress() {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold on");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        ServerConnection.requestServer(getString(R.string.get_user), params, Constant.delete_cart_item, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "get address   >>>   " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        AddressModel addressModel = Helper.getGsonObject().fromJson(response, AddressModel.class);
                        if (!addressModel.getData().isEmpty()) {
                            address_adapter.setData(addressModel.getData());
                        } else {
                            address_recy.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void initRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        address_recy.setItemAnimator(new DefaultItemAnimator());
        address_recy.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        address_recy.setLayoutManager(manager);
        address_recy.setNestedScrollingEnabled(false);
        address_adapter = new AddressAdapter(getActivity(), new AddressAdapter.OnAddressSelectedListner() {
            @Override
            public void onAddressSelected(AddressModel.DataBean model, int pos) {
                address_string = model.getAddress_id();
            }
        });

        address_recy.setAdapter(address_adapter);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cust_recy.setItemAnimator(new DefaultItemAnimator());
        cust_recy.setLayoutManager(manager2);
        cust_recy_adapter = new ViewCustomerOrder(getActivity(), new ViewCustomerOrder.OnCustomerSelectListner() {
            @Override
            public void onSelected(ViewCustomerModel.DataBean model, int pos) {
                if (order_for.equalsIgnoreCase("customer")) {
                    user_id = model.getUser_id();
                }
            }
        });
        cust_recy.setAdapter(cust_recy_adapter);
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
                            cust_recy_adapter.setData(viewCustomerModel.getData(), true);
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

}
