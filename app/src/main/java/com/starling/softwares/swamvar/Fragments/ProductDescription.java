package com.starling.softwares.swamvar.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.ProductColourAdapter;
import com.starling.softwares.swamvar.Adapter.ProductImageAdapter;
import com.starling.softwares.swamvar.Adapter.ProductSizeAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.ProductDescriptionModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescription extends Fragment {
    @BindView(R.id.header_image)
    ImageView header_image;
    @BindView(R.id.name_product)
    TextView name_product;
    @BindView(R.id.photo_recy)
    RecyclerView photo_recy;

    @BindView(R.id.colours_recy)
    RecyclerView colours_recy;
    @BindView(R.id.size_recy)
    RecyclerView size_recy;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.cart)
    Button cart;
    @BindView(R.id.available_quantity)
    TextView available_quantity;


    @BindView(R.id.quantity_note)
    TextView quantity_note;
    private String name, id;
    private ProductImageAdapter photo_adapter;
    private ProductColourAdapter colour_adapter;
    private ProductSizeAdapter size_adapter;
    private String tag = ProductDescription.class.getSimpleName();
    private String price;
    private String colour_id = null, size_id = null;
    private int total_quantity = 1;
    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.price_product)
    TextView price_product;
    private String gst;
    Context context;
    ArrayList<String> arrayList = new ArrayList<>();

    public static ProductDescription newInstance(String name, String id, String price, String gst) {
        ProductDescription productDescription = new ProductDescription();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("id", id);
        bundle.putString("price", price);
        bundle.putString("gst", gst);
        productDescription.setArguments(bundle);
        return productDescription;
    }

    public ProductDescription() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag(getArguments().getString("name"));
    }

    public void setTitle() {
        Home.getInstace().setTitLeFrag(getArguments().getString("name"));
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
//        context=getActivity();
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
        if (getArguments() != null) {
            name = getArguments().getString("name");
            id = getArguments().getString("id");
            price = getArguments().getString("price");
            gst = getArguments().getString("gst");
        }
        name_product.setText(name);
        price_product.setText(String.format("₹ %s + %s%% gst applicable", price, gst));
        initRecycler();
    }

    private void initRecycler() {
        initImageRecycler();
        initColourRecycler();
        initSizeRecycler();
        if (Helper.isDataConnected(getActivity())) {
            getDataFromServer();
        } else Helper.showNoInternetToast(getActivity());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProducttoserver();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home.getInstace().takeToCart();
            }
        });
    }

    private void submitProducttoserver() {
        if (size_id == null) {
            Helper.showToast(getActivity(), "Please select size");
            return;
        }
        if (!Helper.isDataConnected(getActivity())) {
            Helper.showNoInternetToast(getActivity());
            return;
        }
        Helper.logcat(tag, "colour and size id and quantity >>>>   " + colour_id + "  " + size_id + "  " + total_quantity);
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Hold on");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getActivity()).getUser_id());
        params.put("design_id", id);
        params.put("scd_id", colour_id);
        params.put("scd_sid", size_id);
        params.put("quantity", String.valueOf(total_quantity));
        ServerConnection.requestServer(getString(R.string.set_cart), params, Constant.set_cart, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        Helper.showToast(getActivity(), "Product added to cart");
                        Helper.logcat(tag, "submit product to server >>>  " + response);
                    } else {
                        Helper.showToast(getActivity(), "Unable to add product to cart");
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

    private void getDataFromServer() {
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Fetching Data");
        progressDialog.show();
        final HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("design_id", id);
        ServerConnection.requestServer(getString(R.string.get_design_details), params, Constant.get_design_details, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "product information  >>  " + response);
                Helper.dismissDialog(progressDialog);
                if (!response.equalsIgnoreCase("")) {
                    ProductDescriptionModel productDescriptionModel = Helper.getGsonObject().fromJson(response, ProductDescriptionModel.class);
                    if (!productDescriptionModel.getData().isEmpty()) {
                        colour_adapter.setData(productDescriptionModel.getData());
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
                error.printStackTrace();
            }
        });
    }

    private void initSizeRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        size_recy.setItemAnimator(new DefaultItemAnimator());
        size_recy.setLayoutManager(manager);
        size_adapter = new ProductSizeAdapter(getActivity(), new ProductSizeAdapter.OnPicSelectedListner() {
            @Override
            public void onPicSlected(ProductDescriptionModel.DataBean.SizesBean model, int position) {
                size_id = model.getSize_id();
                showBottomSheet(model);
                size_adapter.selectItem(position);
                if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("1")) {
                    available_quantity.setVisibility(View.VISIBLE);
                    available_quantity.setText(String.format("%s items left", model.getAvailable_quantity()));
                } else available_quantity.setVisibility(View.GONE);
            }

            @Override
            public void getSizeId(String size_idd, ProductDescriptionModel.DataBean.SizesBean model) {
                size_id = size_idd;
                if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("1")) {
                    available_quantity.setVisibility(View.VISIBLE);
                    available_quantity.setText(String.format("%s items left", model.getAvailable_quantity()));
                } else available_quantity.setVisibility(View.GONE);
            }

        });
        size_recy.setAdapter(size_adapter);
    }


    private int add_sub_quan = 1;

    private void showBottomSheet(final ProductDescriptionModel.DataBean.SizesBean model) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getLayoutInflater().inflate(R.layout.add_quantity, null);
        final TextView quantity = sheetView.findViewById(R.id.quantity);
        final TextView total = sheetView.findViewById(R.id.total);
        mBottomSheetDialog.setContentView(sheetView);
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
                total_quantity = add_sub_quan;
                quantity_note.setText(String.format("%s items are selected of size %s", String.valueOf(total_quantity), model.getSize()));
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    private void initColourRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        colours_recy.setItemAnimator(new DefaultItemAnimator());
        colours_recy.setLayoutManager(manager);
        colour_adapter = new ProductColourAdapter(getActivity(), new ProductColourAdapter.OnPicSelectedListner() {
            @Override
            public void onPicSlected(ProductDescriptionModel.DataBean model, int position) {
                colour_id = model.getColor_id();
                handleOnClicke(model, position);
            }
        });
        colours_recy.setAdapter(colour_adapter);
    }

    private void handleOnClicke(ProductDescriptionModel.DataBean model, int position) {
        size_adapter.setData(model.getSizes());
        photo_adapter.setData(model.getImages());
        colour_adapter.selectItem(position);
    }

    private void initImageRecycler() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        photo_recy.setItemAnimator(new DefaultItemAnimator());
        photo_recy.setLayoutManager(manager);
        photo_adapter = new ProductImageAdapter(getActivity(), new ProductImageAdapter.OnPicSelectedListner() {
            @Override
            public void onPicSlected(ProductDescriptionModel.DataBean.ImagesBean model, int position) {

                Glide.with(getActivity()).load(model.getDesign_image()).into(header_image);
                photo_adapter.selectItem(position);

            }
        });
        photo_recy.setAdapter(photo_adapter);
    }

}
