package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
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

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.SubCategoryAdapter;
import com.starling.softwares.swamvar.Adapter.SubSubCategroeyAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.SubCategoryModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFrag extends Fragment {

    private SubCategoryAdapter subCategoryAdapter;
    private String cat_id = null;
    private String tag = SubCategoryFrag.class.getSimpleName();
    private SubSubCategroeyAdapter subsubAdapter;

    public static SubCategoryFrag newInstance(String category_id) {
        SubCategoryFrag frag = new SubCategoryFrag();
        Bundle bundle = new Bundle();
        bundle.putString("id", category_id);
        frag.setArguments(bundle);
        return frag;
    }

    @BindView(R.id.sub_cat_recy)
    RecyclerView sub_cat_recy;
    @BindView(R.id.sub_sub_cat_recy)
    RecyclerView sub_sub_cat_recy;

    public SubCategoryFrag() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag(getArguments().getString("Products"));
    }
    public  void setTitle(){
        Home.getInstace().setTitLeFrag(getArguments().getString("Products"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            cat_id = getArguments().getString("id");
        }
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
        if (Helper.isDataConnected(getActivity()))
            getSubCategoryFromServer();
        else Helper.showNoInternetToast(getActivity());
    }

    private void getSubCategoryFromServer() {
        if (cat_id == null) {
            Helper.showToast(getActivity(), "Unable to find subcategories");
            return;
        }
        final ProgressDialog progressDialog = Helper.showProgressDialog(getActivity(), "Getting Sub Categories");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("category_id", cat_id);
        ServerConnection.requestServer(getString(R.string.get_subcat_subsubcat), params, Constant.get_subcat_subsubcat, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                Helper.logcat(tag, "sub categories response >>>>  " + response);
                SubCategoryModel subCategoryModel = Helper.getGsonObject().fromJson(response, SubCategoryModel.class);
                if (!subCategoryModel.getData().isEmpty()) {
                    subCategoryAdapter.setData(subCategoryModel.getData());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
            }
        });
    }

    private void initRecycler() {
        initSubCategoryRecy();
        initSubSubRecy();
    }

    private void initSubSubRecy() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        sub_sub_cat_recy.setItemAnimator(new DefaultItemAnimator());
        sub_sub_cat_recy.setLayoutManager(manager);
        sub_sub_cat_recy.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        sub_sub_cat_recy.setItemAnimator(new DefaultItemAnimator());
        sub_sub_cat_recy.setHasFixedSize(true);
        subsubAdapter = new SubSubCategroeyAdapter(getActivity(), new SubSubCategroeyAdapter.OnSubSubCategorySelctedListner() {
            @Override
            public void onSlected(SubCategoryModel.DataBean.SubSubCategoryBean model) {
                Home.getInstace().takeToProductFragment(Constant.GET_PRODUCT_SUB_SUB_CATEGORY, model.getSub_sub_category_id());

            }
        });
        sub_sub_cat_recy.setAdapter(subsubAdapter);
    }

    private void initSubCategoryRecy() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sub_cat_recy.setItemAnimator(new DefaultItemAnimator());
        sub_cat_recy.setLayoutManager(manager);
        sub_cat_recy.setHasFixedSize(true);
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), new SubCategoryAdapter.OnSubCategorySelectedListner() {
            @Override
            public void onSubCategorySelected(SubCategoryModel.DataBean model) {
                if (!model.getSub_sub_category().isEmpty()) {
                    subsubAdapter.setData(model.getSub_sub_category());
                } else {
                    Helper.showToast(getActivity(), "take to product straight");
                    Home.getInstace().takeToProductFragment(Constant.GET_PRODUCT_SUB_CATEGORY, model.getSub_category_id());
                }
            }
        });
        sub_cat_recy.setAdapter(subCategoryAdapter);
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
