package com.starling.softwares.swamvar.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.SubSubCategroeyAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.SimplySUbSUbCategoryModel;
import com.starling.softwares.swamvar.Model.SubCategoryModel;
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
public class SimplySubCategory extends Fragment {
    @BindView(R.id.sub_sub_cat_recy)
    RecyclerView sub_sub_cat_recy;
    private String cat_id;
    private SubSubCategroeyAdapter subsubAdapter;
    private String tag = SimplySubCategory.class.getSimpleName();
    private String name;
    @BindView(R.id.sub_cat_name)
    TextView sub_cat_name;

    public SimplySubCategory() {
        // Required empty public constructor
    }

    public static SimplySubCategory newInstance(String category_id, String name) {
        SimplySubCategory frag = new SimplySubCategory();
        Bundle bundle = new Bundle();
        bundle.putString("id", category_id);
        bundle.putString("name", name);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();
        Home.getInstace().setTitLeFrag(getArguments().getString("name"));
    }

    public  void setTitle(){
        Home.getInstace().setTitLeFrag(getArguments().getString("name"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simply_sub_category, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            cat_id = getArguments().getString("id");
            name = getArguments().getString("name");
            sub_cat_name.setText(name);
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
        initSubSubRecy();
        if (Helper.isDataConnected(getActivity()))
            getSubCategoryFromServer();
        else Helper.showNoInternetToast(getActivity());
    }


    private void initSubSubRecy() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 3);
        sub_sub_cat_recy.setItemAnimator(new DefaultItemAnimator());
        sub_sub_cat_recy.setLayoutManager(manager);
        sub_sub_cat_recy.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(3), true));
        sub_sub_cat_recy.setItemAnimator(new DefaultItemAnimator());
        sub_sub_cat_recy.setHasFixedSize(true);
        subsubAdapter = new SubSubCategroeyAdapter(getActivity(), new OnSubSubCategorySelctedListner() {
            @Override
            public void onSlected(SimplySUbSUbCategoryModel.DataBean model) {
                Home.getInstace().takeToProductFragment(Constant.GET_PRODUCT_SUB_SUB_CATEGORY, model.getSub_sub_category_id());
            }
        });
        sub_sub_cat_recy.setAdapter(subsubAdapter);
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
        params.put("sub_category_id", cat_id);
        ServerConnection.requestServer(getString(R.string.get_subsub_category), params, Constant.get_subcat_subsubcat, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.dismissDialog(progressDialog);
                Helper.logcat(tag, "sub categories response >>>>  " + response);
                SimplySUbSUbCategoryModel simplySUbSUbCategoryModel = Helper.getGsonObject().fromJson(response, SimplySUbSUbCategoryModel.class);
                subsubAdapter.setData(simplySUbSUbCategoryModel.getData());

            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
            }
        });
    }

    public class SubSubCategroeyAdapter extends RecyclerView.Adapter<SubSubCategroeyAdapter.ViewHolder> {
        private Context context;
        private List<SimplySUbSUbCategoryModel.DataBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private OnSubSubCategorySelctedListner listner;

        public SubSubCategroeyAdapter(Context context, OnSubSubCategorySelctedListner listner) {
            this.context = context;
            this.listner = listner;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.sub_sub_category_item, parent, false);
            return new ViewHolder(view);
        }

        public void setData(List<SimplySUbSUbCategoryModel.DataBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final SimplySUbSUbCategoryModel.DataBean model = list.get(position);
            holder.timings.setText(model.getSubsubcategory_name());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onSlected(model);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.timings)
            TextView timings;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


    }

    public interface OnSubSubCategorySelctedListner {
        void onSlected(SimplySUbSUbCategoryModel.DataBean model);
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
