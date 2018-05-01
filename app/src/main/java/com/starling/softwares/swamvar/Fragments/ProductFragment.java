package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.ProductModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    @BindView(R.id.my_recycler)
    RecyclerView my_recycler;
    private ProductAdapter my_recycler_adapter;
    private String id_to_send = null;
    private final int GET_PRODUCT_CATEGORY = 0;
    private final int GET_PRODUCT_SUB_CATEGORY = 1;
    private final int GET_PRODUCT_SUB_SUB_CATEGORY = 2;
    private static String tag = ProductFragment.class.getSimpleName();

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(int type, String id) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        if (type == Constant.GET_PRODUCT_CATEGORY) {
            bundle.putString("cat_id", id);
        } else if (type == Constant.GET_PRODUCT_SUB_CATEGORY) {
            bundle.putString("sub_id", id);
        } else if (type == Constant.GET_PRODUCT_SUB_SUB_CATEGORY) {
            bundle.putString("sub_sub_id", id);
        }
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag("Products");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
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
        initProductThreeRecycler();
        if (getArguments().containsKey("cat_id")) {
            id_to_send = getArguments().getString("cat_id");
            getProductsFromServer(GET_PRODUCT_CATEGORY);
            Helper.logcat(tag, "11");
        } else if (getArguments().containsKey("sub_id")) {
            id_to_send = getArguments().getString("sub_id");
            getProductsFromServer(GET_PRODUCT_SUB_CATEGORY);
            Helper.logcat(tag, "12");
        } else if (getArguments().containsKey("sub_sub_id")) {
            id_to_send = getArguments().getString("sub_sub_id");
            getProductsFromServer(GET_PRODUCT_SUB_SUB_CATEGORY);
            Helper.logcat(tag, "13");
        }
    }


    private void getProductsFromServer(int i) {
        if (id_to_send == null) return;
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Getting Products");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        if (i == GET_PRODUCT_CATEGORY) {
            Helper.logcat(tag, "21");
            params.put("category_id", id_to_send);
        } else if (i == GET_PRODUCT_SUB_CATEGORY) {
            Helper.logcat(tag, "22");
            params.put("sub_category_id", id_to_send);
        } else if (i == GET_PRODUCT_SUB_SUB_CATEGORY) {
            Helper.logcat(tag, "23");
            params.put("sub_sub_category_id", id_to_send);
        }
        ServerConnection.requestServer(getString(R.string.get_design), params, Constant.get_design, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "getProductsFromServer  >>>  " + response);
                Helper.dismissDialog(progressDialog);
                ProductModel productModel = Helper.getGsonObject().fromJson(response, ProductModel.class);
                if (!productModel.getData().isEmpty()) {
                    my_recycler_adapter.setData(productModel.getData());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
            }
        });
    }


    private void initProductThreeRecycler() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        my_recycler.setItemAnimator(new DefaultItemAnimator());
        my_recycler.setLayoutManager(manager);
        my_recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        my_recycler_adapter = new ProductAdapter(getActivity(), new onProductSelectedListner() {
            @Override
            public void onProductSelected(ProductModel.DataBean model) {
                Home.getInstace().takeToProductDescriptionPage(model.getDesign_name(), model.getMrp(), model.getDesign_id(), model.getGst());
            }
        });
        my_recycler.setAdapter(my_recycler_adapter);
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private Context context;
        private List<ProductModel.DataBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private onProductSelectedListner listner;

        public ProductAdapter(Context context, onProductSelectedListner listner) {
            this.context = context;
            this.listner = listner;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.product_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ProductModel.DataBean model = list.get(position);
            Glide.with(context).load(model.getDesign_picture()).into(holder.iconId);
            holder.name.setText(model.getDesign_name());
            holder.price.setText("\u20B9 " + model.getMrp());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onProductSelected(model);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<ProductModel.DataBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iconId)
            ImageView iconId;
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.price)
            TextView price;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface onProductSelectedListner {
        void onProductSelected(ProductModel.DataBean model);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
