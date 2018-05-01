package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Model.CartModel;
import com.starling.softwares.swamvar.Model.ViewCustomerModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/9/2018.
 */

public class ViewCustomerOrder extends RecyclerView.Adapter<ViewCustomerOrder.ViewHolder> {
    private Context context;
    private List<ViewCustomerModel.DataBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnCustomerSelectListner listner;
    boolean isOrderPlace = false;
    private SparseBooleanArray sparseBooleanArray;

    public ViewCustomerOrder(Context context, OnCustomerSelectListner listner) {
        this.context = context;
        this.listner = listner;
        sparseBooleanArray = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ViewCustomerModel.DataBean model = list.get(position);
        holder.cart_product_name.setText(model.getName().toUpperCase());
        holder.cart_product_price.setText(String.format("%s\n%s", model.getMobile(), model.getEmail()));
        holder.cart_product_image.setImageDrawable(getTextDrawableBuilder().build(Utils.getActiveUser(context).getUser_name().substring(0, 1).toUpperCase(), ContextCompat.getColor(context, R.color.colorPrimary)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOrderPlace) {
                    setItemSelected(holder.getAdapterPosition());
                }
                listner.onSelected(model, holder.getAdapterPosition());

            }
        });

        if (isOrderPlace) {
            if (sparseBooleanArray.get(holder.getAdapterPosition(), false)) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_grey));
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            holder.view_order.setVisibility(View.GONE);
        }
    }

    public void setItemSelected(int pos) {
        if (sparseBooleanArray.size() > 0) {
            sparseBooleanArray.clear();
        }
        sparseBooleanArray.put(pos, true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public TextDrawable.IBuilder getTextDrawableBuilder() {
        return TextDrawable.builder()
                .beginConfig()
                .textColor(ContextCompat.getColor(context, R.color.white))
                .bold()
                .withBorder(4)
                .height(100)
                .width(100)
                .toUpperCase()

                .endConfig()
                .round();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_product_image)
        ImageView cart_product_image;
        @BindView(R.id.cart_product_name)
        TextView cart_product_name;
        @BindView(R.id.cart_product_price)
        TextView cart_product_price;
        @BindView(R.id.view_order)
        AppCompatTextView view_order;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<ViewCustomerModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setData(List<ViewCustomerModel.DataBean> list, boolean isOrderPlace) {
        this.list = list;
        this.isOrderPlace = isOrderPlace;
        notifyDataSetChanged();
    }

    public interface OnCustomerSelectListner {
        void onSelected(ViewCustomerModel.DataBean model, int pos);
    }

    public List<ViewCustomerModel.DataBean> getItems() {
        return list;
    }
}
