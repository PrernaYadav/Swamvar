package com.starling.softwares.swamvar.Fragments;


import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.BatchAdapter;
import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {
    @BindView(R.id.categ_recy)
    RecyclerView categ_recy;
    @BindView(R.id.my_recycler_one)
    RecyclerView my_recycler_one;
    @BindView(R.id.my_recycler_two)
    RecyclerView my_recycler_two;
    @BindView(R.id.my_recycler_three)
    RecyclerView my_recycler_three;
    private String tag = LandingFragment.class.getSimpleName();
    private BatchAdapter batchAdapter;
    @BindView(R.id.name_one)
    TextView name_one;
    @BindView(R.id.name_two)
    TextView name_two;
    @BindView(R.id.name_three)
    TextView name_three;
    private ProductAdapter product_one_recy;
    private ProductAdapter my_recycler_two_adapter;
    private ProductAdapter my_recycler_three_adapter;

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_home);
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
        initRecycler();
        if (Helper.isDataConnected(getActivity())) {
            getDataFromServer();
        }
    }

    private void initRecycler() {
        initBatchRecycler();
        initProductRecycler();
        initProducttwoRecycler();
        initProductThreeRecycler();
    }

    private void initProductThreeRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        my_recycler_three.setItemAnimator(new DefaultItemAnimator());
        my_recycler_three.setLayoutManager(manager);
        my_recycler_three_adapter = new ProductAdapter(getActivity(), new onProductSelectedListner() {
            @Override
            public void onProductSelected(LandingPageModel.CatDesignsBean.DesignsBean model) {
                Home.getInstace().takeToProductDescriptionPage(model.getDesign_name(), model.getMrp(), model.getDesign_id(), model.getGst());
            }
        });
        my_recycler_three.setAdapter(my_recycler_three_adapter);
    }

    private void initProducttwoRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        my_recycler_two.setItemAnimator(new DefaultItemAnimator());
        my_recycler_two.setLayoutManager(manager);
        my_recycler_two_adapter = new ProductAdapter(getActivity(), new onProductSelectedListner() {
            @Override
            public void onProductSelected(LandingPageModel.CatDesignsBean.DesignsBean model) {
                Home.getInstace().takeToProductDescriptionPage(model.getDesign_name(), model.getMrp(), model.getDesign_id(), model.getGst());
            }
        });
        my_recycler_two.setAdapter(my_recycler_two_adapter);
    }

    private void initProductRecycler() {
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        my_recycler_one.setItemAnimator(new DefaultItemAnimator());
        my_recycler_one.setLayoutManager(manager);
        product_one_recy = new ProductAdapter(getActivity(), new onProductSelectedListner() {
            @Override
            public void onProductSelected(LandingPageModel.CatDesignsBean.DesignsBean model) {
                Home.getInstace().takeToProductDescriptionPage(model.getDesign_name(), model.getMrp(), model.getDesign_id(), model.getGst());
            }
        });
        my_recycler_one.setAdapter(product_one_recy);
    }

    private void initBatchRecycler() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categ_recy.setItemAnimator(new DefaultItemAnimator());
        categ_recy.setLayoutManager(manager);
        batchAdapter = new BatchAdapter(getActivity(), new OnBatchSelectedListner() {
            @Override
            public void onCategorySelected(LandingPageModel.CategoriesBean mode) {
                Home.getInstace().takeTosubCategory(mode);
            }

        });
        categ_recy.setAdapter(batchAdapter);
    }

    private void getDataFromServer() {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        ServerConnection.requestServer(getString(R.string.get_data), params, Constant.get_data, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "getDataFromServer >>  " + response);
                handleHomePageResponse(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void handleHomePageResponse(String response) {
        try {
            if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                LandingPageModel model = Helper.getGsonObject().fromJson(response, LandingPageModel.class);
                if (!model.getCategories().isEmpty()) {
                    batchAdapter.setData(model.getCategories());
                }
                if (!model.getCatDesigns().isEmpty()) {
                    for (int i = 0; i < model.getCatDesigns().size(); i++) {
                        if (i == 0) {
                            if (!model.getCatDesigns().get(i).getDesigns().isEmpty()) {
                                name_one.setText(model.getCatDesigns().get(i).getTitle());
                                product_one_recy.setData(model.getCatDesigns().get(i).getDesigns());
                            }
                        } else if (i == 1) {
                            name_two.setText(model.getCatDesigns().get(i).getTitle());
                            my_recycler_two_adapter.setData(model.getCatDesigns().get(i).getDesigns());
                        } else if (i == 2) {
                            name_three.setText(model.getCatDesigns().get(i).getTitle());
                            my_recycler_three_adapter.setData(model.getCatDesigns().get(i).getDesigns());
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private Context context;
        private List<LandingPageModel.CatDesignsBean.DesignsBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private onProductSelectedListner listner;

        public ProductAdapter(Context context, onProductSelectedListner listner) {
            this.context = context;
            this.listner = listner;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.product_home_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
            final LandingPageModel.CatDesignsBean.DesignsBean model = list.get(position);
            Helper.logcat(tag, "images link >>>   " + model.getDesign_image());
            Glide.with(context).load(model.getDesign_image()).into(holder.iconId);
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

        public void setData(List<LandingPageModel.CatDesignsBean.DesignsBean> list) {
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
        void onProductSelected(LandingPageModel.CatDesignsBean.DesignsBean model);
    }

}
