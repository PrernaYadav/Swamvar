package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.ViewCustomerOrder;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
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
public class ShowAddCustomer extends Fragment {
    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.mobile)
    TextInputEditText mobile;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.address)
    TextInputEditText address;
    @BindView(R.id.gst)
    TextInputEditText gst;
    @BindView(R.id.add)
    Button add;
    private String tag = ShowAddCustomer.class.getSimpleName();
    private ViewCustomerOrder cust_recy_adapter;

    public ShowAddCustomer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_add_customer, container, false);
        ButterKnife.bind(this, view);
        getActivity().getFragmentManager().popBackStack();
        initView();

        return view;
    }

    private void initView() {

    }


}
