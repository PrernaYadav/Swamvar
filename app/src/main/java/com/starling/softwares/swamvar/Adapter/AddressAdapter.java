package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.starling.softwares.swamvar.Interfaces.OnBatchSelectedListner;
import com.starling.softwares.swamvar.Model.AddressModel;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private List<AddressModel.DataBean> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnAddressSelectedListner listner;
    private SparseBooleanArray sparseBooleanArray;

    public AddressAdapter(Context context, OnAddressSelectedListner listner) {
        this.context = context;
        this.listner = listner;
        sparseBooleanArray = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AddressModel.DataBean model = list.get(position);
        holder.radio.setText(model.getName());
        holder.address.setText(String.format("%s near %s\n%s,%s-%s", model.getAddress(), model.getLandmark(), model.getCity_name(), model.getState_name(), model.getPincode()));
        holder.mobile.setText(model.getMobile());
        holder.bindData(model, holder.getAdapterPosition());
        if (sparseBooleanArray.get(position, false)) {
            holder.radio.setChecked(true);
        } else {
            holder.radio.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listner.onAddressSelected(model, holder.getAdapterPosition());
                setCheckbox(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCheckbox(int position) {
        if (sparseBooleanArray.size() > 0) {
            sparseBooleanArray.clear();
        }
        sparseBooleanArray.put(position, true);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mobile)
        TextView mobile;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.radio)
        CheckBox radio;

        private int position;
        private AddressModel.DataBean model;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindData(AddressModel.DataBean model, int adapterPosition) {
            this.model = model;
            this.position = adapterPosition;
        }


    }


    public interface OnAddressSelectedListner {
        void onAddressSelected(AddressModel.DataBean model, int pos);
    }


    public void setData(List<AddressModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
