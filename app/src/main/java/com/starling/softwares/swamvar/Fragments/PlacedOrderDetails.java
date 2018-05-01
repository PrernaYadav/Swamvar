package com.starling.softwares.swamvar.Fragments;


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
import android.widget.CheckBox;
import android.widget.TextView;

import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.OrdersByIdAdapter;
import com.starling.softwares.swamvar.Adapter.PlaceOrderDetailsAdapter;
import com.starling.softwares.swamvar.Model.OrdersByIdModel;
import com.starling.softwares.swamvar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacedOrderDetails extends Fragment {
    @BindView(R.id.order_recy)
    RecyclerView order_recy;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.radio)
    TextView radio;
    private OrdersByIdModel.DataBean model;

    public PlacedOrderDetails() {
        // Required empty public constructor
    }

    public static PlacedOrderDetails newInstance(OrdersByIdModel.DataBean user_id) {
        PlacedOrderDetails ordersById = new PlacedOrderDetails();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", user_id);
        ordersById.setArguments(bundle);
        return ordersById;
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Order Details");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placed_order_details, container, false);
        ButterKnife.bind(this, view);
        model = (OrdersByIdModel.DataBean) getArguments().getSerializable("model");
        setHasOptionsMenu(true);
        initView(model);
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


    private void initView(OrdersByIdModel.DataBean model) {
        radio.setText(model.getShipping_address().get(0).getName().toUpperCase());
        mobile.setText(model.getShipping_address().get(0).getMobile().toUpperCase());
        address.setText(String.format("%s near %s\n%s,%s-%s", model.getShipping_address().get(0).getAddress(), model.getShipping_address().get(0).getLandmark()
                , model.getShipping_address().get(0).getCity(), model.getShipping_address().get(0).getState(), model.getShipping_address().get(0).getPincode()));
        initRecycler(model);

    }

    private void initRecycler(OrdersByIdModel.DataBean model) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        order_recy.setItemAnimator(new DefaultItemAnimator());
        order_recy.setLayoutManager(manager);
        PlaceOrderDetailsAdapter orderByIdAdapter = new PlaceOrderDetailsAdapter(getActivity());
        order_recy.setAdapter(orderByIdAdapter);
        orderByIdAdapter.setData(model.getOrder_list());
    }

}
