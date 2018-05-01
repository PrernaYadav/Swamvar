package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starling.softwares.swamvar.Model.SubCategoryModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/5/2018.
 */

public class SubSubCategroeyAdapter extends RecyclerView.Adapter<SubSubCategroeyAdapter.ViewHolder> {
    private Context context;
    private List<SubCategoryModel.DataBean.SubSubCategoryBean> list = new ArrayList<>();
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

    public void setData(List<SubCategoryModel.DataBean.SubSubCategoryBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubCategoryModel.DataBean.SubSubCategoryBean model = list.get(position);
        Glide.with(context).load(model.getSubsubcategory_image()).into(holder.image);
        holder.timings.setText(model.getSub_sub_category_name());
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
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSubSubCategorySelctedListner {
        void onSlected(SubCategoryModel.DataBean.SubSubCategoryBean model);
    }
}
