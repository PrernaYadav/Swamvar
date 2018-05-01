package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.CartAdapter;
import com.starling.softwares.swamvar.Adapter.ProductSizeAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.CartModel;
import com.starling.softwares.swamvar.Model.ProductDescriptionModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    @BindView(R.id.cart_empty)
    LinearLayout cart_empty;
    @BindView(R.id.cart_recycler)
    RecyclerView cart_recycler;
    @BindView(R.id.cart_footer)
    CardView cart_footer;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.total_items)
    TextView total_items;
    @BindView(R.id.next)
    Button next;
    private CartAdapter cart_adapter;
    private String tag = CartFragment.class.getSimpleName();

    private Double cart_total = 0.0;
    private int add_sub_quan = 1;
    private boolean value = false;


    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Your Cart");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initView();
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


    private void initView() {
        initRecy();
        if (Helper.isDataConnected(getActivity())) {
            getDataFromServer();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart_adapter.getItems().isEmpty()) {
                    return;
                }

                if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("1")) {

                }
                makeJsonAndSubmit();
            }
        });
    }


    private void getDataFromServer() {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Please wait");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        ServerConnection.requestServer(getString(R.string.get_cart), params, Constant.get_cart, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                Helper.logcat(tag, "getDataFromServer cart >>  " + response);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        CartModel cartModel = Helper.getGsonObject().fromJson(response, CartModel.class);
                        if (cartModel.getData().isEmpty()) {
                            cart_recycler.setVisibility(View.GONE);
                            cart_empty.setVisibility(View.VISIBLE);
                        } else {
                            cart_adapter.setData(cartModel.getData());
                            calculateTotalCost(cartModel.getData());
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

    private void calculateTotalCost(List<CartModel.DataBean> data) {
        double costToadd = 0.0;
        double percentToAdd = 0.0;
        cart_total = 0.0;
        for (int i = 0; i < data.size(); i++) {
            CartModel.DataBean model = data.get(i);
            Helper.logcat(tag, "cost after gst >>>   " + getCostAfterGst(model) + "   " + model.getMrp() + "   " + model.getGst());
            costToadd = (Double.valueOf(data.get(i).getMrp()) + getCostAfterGst(model)) * Integer.parseInt(model.getQuantity());
            cart_total = cart_total + costToadd;
            Helper.logcat(tag, "price addition >>  " + cart_total + "  item count " + model.getQuantity());
        }
        total_price.setText("₹ " + String.valueOf(cart_total));
        total_items.setText(String.format("%d items", data.size()));
    }

    private double getCostAfterGst(CartModel.DataBean data) {
        return (Double.parseDouble(data.getMrp()) / 100.0) * Double.parseDouble(data.getGst());
    }


    private void initRecy() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cart_recycler.setItemAnimator(new DefaultItemAnimator());
        cart_recycler.setLayoutManager(manager);
        cart_adapter = new CartAdapter(getActivity(), new CartAdapter.OnItemDeleteListner() {
            @Override
            public void onDelete(CartModel.DataBean model, int position) {
                deleteItem(model, position);

            }

            @Override
            public void onEdit(CartModel.DataBean model, final int position) {
                showBottomSheet(model, position, new OnSizeEditedListner() {

                    @Override
                    public void onOnClicked(CartModel.DataBean model, int pos) {
                        cart_adapter.notifyItemChanged(position);
                        calculateTotalCost(cart_adapter.getItems());
                    }

                    @Override
                    public void onCancelClicked(CartModel.DataBean model, int pos) {

                    }
                });

            }
        });
        cart_recycler.setAdapter(cart_adapter);
    }

    private void deleteItem(final CartModel.DataBean model, final int position) {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold on");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("cart_id", model.getCart_id());
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        ServerConnection.requestServer(getString(R.string.delete_cart_item), params, Constant.delete_cart_item, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        cart_adapter.removeItem(model, position);
                        calculateTotalCost(cart_adapter.getItems());
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


    public interface OnSizeEditedListner {
        void onOnClicked(CartModel.DataBean model, int pos);

        void onCancelClicked(CartModel.DataBean model, int pos);
    }

    private void showBottomSheet(final CartModel.DataBean model, final int position, final OnSizeEditedListner onSizeEditedListner) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getLayoutInflater().inflate(R.layout.add_quantity, null);
        final TextView quantity = sheetView.findViewById(R.id.quantity);
        final TextView total = sheetView.findViewById(R.id.total);
        mBottomSheetDialog.setContentView(sheetView);
        add_sub_quan = Integer.parseInt(model.getQuantity());
        quantity.setText(String.valueOf(add_sub_quan));
        sheetView.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_sub_quan == 1) return;
                add_sub_quan--;
                quantity.setText(String.valueOf(add_sub_quan));
                total.setText(String.format("%s items are selected of size %s", String.valueOf(add_sub_quan), model.getSize()));

            }
        });
        sheetView.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_sub_quan == 5) return;
                add_sub_quan++;
                quantity.setText(String.valueOf(add_sub_quan));
                total.setText(String.format("%s items are selected of size %s", String.valueOf(add_sub_quan), model.getSize()));


            }
        });
        sheetView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        sheetView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendDataToServer(quantity.getText().toString(), position, model, new OnSizeEditedListner() {
                    @Override
                    public void onOnClicked(CartModel.DataBean model, int pos) {
                        model.setQuantity(quantity.getText().toString());
                        onSizeEditedListner.onOnClicked(model, pos);
                    }

                    @Override
                    public void onCancelClicked(CartModel.DataBean model, int pos) {

                    }
                });
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    private void sendDataToServer(String quantity, final int position, final CartModel.DataBean model, final OnSizeEditedListner onSizeEditedListner) {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold On");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        params.put("cart_id", model.getCart_id());
        params.put("quantity", quantity);
        ServerConnection.requestServer(getString(R.string.update_quantity), params, Constant.update_quantity, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        onSizeEditedListner.onOnClicked(model, position);
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

    private void makeJsonAndSubmit() {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
            object.put("ttl_amount", String.valueOf(cart_total));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cart_adapter.getItems().size(); i++) {
            CartModel.DataBean model = cart_adapter.getItems().get(i);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cart_id", model.getCart_id());
                jsonObject.put("mrp_product", model.getMrp());
                jsonObject.put("ttl_amount", model.getPricewithoutgst());
                jsonObject.put("gst", model.getGst());
                jsonObject.put("amount_after_gst", model.getTotal_price());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(jsonObject);
        }
        try {
            object.put("products", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Helper.logcat(tag, "new json array formed going to next page >>>  " + object.toString());

        isAddressAvailable(object.toString());
    }

    private boolean isAddressAvailable(final String object) {

        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold On");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());

        ServerConnection.requestServer(getString(R.string.check_address_status), params, Constant.update_quantity, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "check address status >>>   " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        if (new JSONObject(response).getBoolean("address_status")) {
                            Home.getInstace().takeToAddressPage(object.toString());
                        } else {
                            Home.getInstace().takeToAddressAddPage(object.toString());
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
        return value;
    }


}
