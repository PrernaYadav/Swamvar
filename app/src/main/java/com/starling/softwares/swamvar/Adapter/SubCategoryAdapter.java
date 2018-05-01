package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.SubCategoryModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/5/2018.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private Context context;
    private List<SubCategoryModel.DataBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnSubCategorySelectedListner listner;
    private SparseBooleanArray selectedItem;

    public SubCategoryAdapter(Context context, OnSubCategorySelectedListner listner) {
        this.context = context;
        this.listner = listner;
        selectedItem = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_category_item_list, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<SubCategoryModel.DataBean> list) {
        this.list = list;

        notifyDataSetChanged();
        if (this.list.size() > 0) {
            SetItemSelected(0);
            listner.onSubCategorySelected(this.list.get(0));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SubCategoryModel.DataBean model = list.get(position);

        if (model.getSub_category().contains(" ")) {
            String[] name = model.getSub_category().split(" ");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < name.length; i++) {
                if (i < name.length - 1) {
                    builder.append(name[i] + "\n");
                } else {
                    builder.append(name[i]);
                }
            }
            holder.sub_cat
                    .setText(builder.toString());
        } else {
            holder.sub_cat.setText(model.getSub_category());
        }
        if (selectedItem.get(position, false)) {
            holder.parent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.parent.setBackgroundColor(ContextCompat.getColor(context, R.color.btn_login_bg));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onSubCategorySelected(model);
                SetItemSelected(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void SetItemSelected(int position) {
        if (selectedItem.size() > 0) {
            selectedItem.clear();
        }
        selectedItem.put(position, true);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sub_cat)
        TextView sub_cat;
        @BindView(R.id.parent)
        RelativeLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSubCategorySelectedListner {
        void onSubCategorySelected(SubCategoryModel.DataBean model);
    }
}
