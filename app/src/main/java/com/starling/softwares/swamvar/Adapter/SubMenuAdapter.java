package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starling.softwares.swamvar.Model.MenuModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Helper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/4/2018.
 */

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MenuModel.DataBean.SubCategoryBean> list = new ArrayList<>();
    private OnSubmenuSelectedListner onSubmenuSelectedListner;

    public SubMenuAdapter(Context context, OnSubmenuSelectedListner onSubmenuSelectedListner) {
        this.context = context;
        this.onSubmenuSelectedListner = onSubmenuSelectedListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sub_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MenuModel.DataBean.SubCategoryBean model = list.get(position);
        holder.item.setText(model.getSubcategory_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.logcat("tag", "sub category name and id >>>  " + model.getSubcategory_name() + "   " + model.getSubcategory_id());
                onSubmenuSelectedListner.onSelected(model);
            }
        });
    }

    public void setData(List<MenuModel.DataBean.SubCategoryBean> model) {
        this.list = model;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnSubmenuSelectedListner {
        void onSelected(MenuModel.DataBean.SubCategoryBean model);
    }
}
