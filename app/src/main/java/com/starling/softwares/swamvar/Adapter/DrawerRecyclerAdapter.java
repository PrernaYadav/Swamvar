package com.starling.softwares.swamvar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starling.softwares.swamvar.Model.ItemCategory;
import com.starling.softwares.swamvar.Model.MenuModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 1/4/2018.
 */

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_CATEGORY = 1;
    private static final int TYPE_ITEM_PAGE = 2;

    DrawerRecyclerInterface drawerRecyclerInterface;
    private LayoutInflater layoutInflater;
    private Context context;

    private List<MenuModel.DataBean> categoryList = new ArrayList<>();
    private List<ItemCategory> itemList = new ArrayList<>();

    public DrawerRecyclerAdapter(DrawerRecyclerInterface drawerRecyclerInterface, Context context) {
        this.drawerRecyclerInterface = drawerRecyclerInterface;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM_CATEGORY) {
            View view = layoutInflater.inflate(R.layout.list_item_drawer_category, parent, false);
            return new categoryViewHOlder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.list_page_drawer_category, parent, false);
            return new ItemViewHOlder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof categoryViewHOlder) {
            categoryViewHOlder viewHOlder = (categoryViewHOlder) holder;
            final MenuModel.DataBean model = categoryList.get(position);
            viewHOlder.category.setText(model.getCategory_name());
            viewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerRecyclerInterface.onCategorySelected(model);
                }
            });
        } else if (holder instanceof ItemViewHOlder) {
            ItemViewHOlder itemViewHOlder = (ItemViewHOlder) holder;
            final ItemCategory model = itemList.get(position - categoryList.size());
            itemViewHOlder.item.setText(model.getCategoryName());
            itemViewHOlder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerRecyclerInterface.onItemSlected(model);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size() + itemList.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (position <= categoryList.size() - 1) {
            return TYPE_ITEM_CATEGORY;
        } else {
            return TYPE_ITEM_PAGE;
        }
    }

    public class categoryViewHOlder extends RecyclerView.ViewHolder {
        @BindView(R.id.parent)
        RelativeLayout parent;

        @BindView(R.id.category)
        TextView category;

        public categoryViewHOlder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemViewHOlder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        TextView item;

        public ItemViewHOlder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface DrawerRecyclerInterface {
        void onCategorySelected(MenuModel.DataBean model);

        void onItemSlected(ItemCategory itemCategory);
    }

    public void addCategory(List<MenuModel.DataBean> list) {
        this.categoryList = list;
        notifyDataSetChanged();
    }

    public void addItem(List<ItemCategory> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }


}
