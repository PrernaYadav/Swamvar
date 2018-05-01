package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.OrdersByIdModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/10/2018.
 */

public class OrdersByIdAdapter extends RecyclerView.Adapter<OrdersByIdAdapter.ViewHolder> {
    private Context context;
    private List<OrdersByIdModel.DataBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnOrderviewListner listner;

    public OrdersByIdAdapter(Context context, OnOrderviewListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orders_item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrdersByIdModel.DataBean model = list.get(position);
        holder.invoice.setText(model.getOrder_date());
        holder.name.setText(model.getShipping_address().get(0).getName());
        holder.date.setText(model.getShipping_address().get(0).getMobile());
        holder.rate.setText("Amount Paid : ₹ " + model.getTtl_amount());
        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onView(model, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.invoice)
        TextView invoice;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.view_details)
        TextView view_details;
        @BindView(R.id.rate)
        TextView rate;


        private int position;
        private OrdersByIdModel.DataBean model;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnOrderviewListner {
        void onView(OrdersByIdModel.DataBean model, int position);
    }


    public void setData(List<OrdersByIdModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
