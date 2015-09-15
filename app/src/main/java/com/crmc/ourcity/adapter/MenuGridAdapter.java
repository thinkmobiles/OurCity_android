package com.crmc.ourcity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.menu.MenuModel;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by podo on 05.08.15.
 */
public class MenuGridAdapter extends RecyclerView.Adapter<MenuGridAdapter.ViewHolder> {

    private List<MenuModel> mMenuModels;
    private Context mContext;

    public MenuGridAdapter(List<MenuModel> _mMenuModels, Context _context) {
        this.mMenuModels = _mMenuModels;
        this.mContext = _context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
        View view = LayoutInflater.from(_parent.getContext()).inflate(R.layout.menu_grid_item, _parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, int _position) {
        MenuModel item = mMenuModels.get(_position);
        if(!TextUtils.isEmpty(item.colorItem)) {
            Image.setBackgroundColorView(mContext, _holder.llMenuItem, R.drawable.item_boarder_menu, Color.parseColor(item.colorItem));
        }
        if (!TextUtils.isEmpty(item.borderColor)) {
            Image.setBorderColorView(mContext, _holder.llMenuItem, R.drawable.item_boarder_menu, Color.parseColor(item.borderColor),
                   item.borderWidth);
            Log.d("TAGG", item.borderColor + " Boarder");
        }
        _holder.ivMenuIcon.setImageBitmap(Image.convertBase64ToBitmap(item.iconItem));
        _holder.tvTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return mMenuModels.size();
    }

    public MenuModel getItem(int _position) {
        return mMenuModels.get(_position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivMenuIcon;
        private LinearLayout llMenuItem;

        public ViewHolder(View _itemView) {
            super(_itemView);
            tvTitle = (TextView) _itemView.findViewById(R.id.tvTitle_MF);
            ivMenuIcon = (ImageView) _itemView.findViewById(R.id.ivMenuIcon_MF);
            llMenuItem = (LinearLayout) _itemView.findViewById(R.id.llMenuItem_MF);
        }
    }
}
