package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Model.CartModel;
import com.starling.softwares.swamvar.Model.OrdersByIdModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/11/2018.
 */

public class PlaceOrderDetailsAdapter extends RecyclerView.Adapter<PlaceOrderDetailsAdapter.ViewHolder> {
    private Context context;
    private List<OrdersByIdModel.DataBean.OrderListBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public PlaceOrderDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrdersByIdModel.DataBean.OrderListBean model = list.get(position);
        Glide.with(context).load(model.getDesign_image()).into(holder.cart_product_image);
        holder.cart_product_name.setText(String.format("%s (%s)", model.getDesign_name(), model.getDesign_number()));
        holder.cart_product_price.setText("₹ " + model.getMrp_product() + " + (" + model.getGst() + "% gst)");
        holder.cart_product_quantity.setText(String.format("%s items \nof size %s", model.getQuantity(), model.getSize()));
        holder.colour_code.setBackgroundColor(Color.parseColor(model.getColor_code()));
        holder.total_price.setText("Total Amount : ₹ " + model.getAmount_after_gst());
        holder.edit_quantity.setText(model.getColor_name());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_product_image)
        ImageView cart_product_image;
        @BindView(R.id.cart_product_name)
        TextView cart_product_name;
        @BindView(R.id.cart_product_price)
        TextView cart_product_price;
        @BindView(R.id.cart_product_quantity)
        TextView cart_product_quantity;
        @BindView(R.id.colour_code)
        View colour_code;
        @BindView(R.id.edit_quantity)
        TextView edit_quantity;
        @BindView(R.id.total_price)
        TextView total_price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<OrdersByIdModel.DataBean.OrderListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
