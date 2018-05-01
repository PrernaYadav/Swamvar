package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shobh on 7/31/2017.
 */

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> {
    private Context context;
    private List<LandingPageModel.CategoriesBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnBatchSelectedListner listner;

    public BatchAdapter(Context context, OnBatchSelectedListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.batch_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LandingPageModel.CategoriesBean model = list.get(position);

        holder.timings.setText(model.getTitle().toUpperCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onCategorySelected(model);
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

        private int position;
        private LandingPageModel.CategoriesBean model;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setData(List<LandingPageModel.CategoriesBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
