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
import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Model.CartModel;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/8/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartModel.DataBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemDeleteListner listner;

    public CartAdapter(Context context, OnItemDeleteListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CartModel.DataBean model = list.get(position);
        Glide.with(context).load(model.getDesign_image()).into(holder.cart_product_image);
        holder.cart_product_name.setText(String.format("%s (%s)", model.getDesign_name(), model.getDesign_number()));
        holder.cart_product_price.setText("₹ " + model.getMrp() + " + (" + model.getGst() + "% gst)");
        holder.cart_product_quantity.setText(String.format("%s items \nof size %s", model.getQuantity(), model.getSize()));
        holder.colour_code.setBackgroundColor(Color.parseColor(model.getColor_code()));
        model.setPricewithoutgst(String.valueOf((Double.valueOf(model.getMrp()) * Integer.parseInt(model.getQuantity()))));
        model.setTotal_price(String.valueOf((Double.valueOf(model.getMrp()) + getCostAfterGst(model)) * Integer.parseInt(model.getQuantity())));

        holder.total_price.setText("Total Amount : ₹ " + model.getTotal_price());

        holder.cart_product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onDelete(model, holder.getAdapterPosition());
            }
        });
        holder.edit_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onEdit(model, holder.getAdapterPosition());
            }
        });
    }


    private double getCostAfterGst(CartModel.DataBean data) {
        return (Double.parseDouble(data.getMrp()) / 100.0) * Double.parseDouble(data.getGst());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(CartModel.DataBean model, int position) {
        if (list.contains(model)) {
            list.remove(model);
            notifyItemRemoved(position);
        }
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
        @BindView(R.id.cart_product_delete)
        TextView cart_product_delete;
        @BindView(R.id.colour_code)

        View colour_code;
        @BindView(R.id.edit_quantity)
        TextView edit_quantity;
        @BindView(R.id.total_price)
        TextView total_price;

        private int position;
        private CartModel.DataBean model;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<CartModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemDeleteListner {
        void onDelete(CartModel.DataBean model, int position);

        void onEdit(CartModel.DataBean model, int position);
    }

    public List<CartModel.DataBean> getItems() {
        return list;
    }
}
