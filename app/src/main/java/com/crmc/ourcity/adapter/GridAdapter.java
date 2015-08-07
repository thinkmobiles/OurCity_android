package com.crmc.ourcity.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fragment.SubMenuFragment;
import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.util.List;

/**
 * Created by podo on 05.08.15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private List<MenuModel> mMenuModels;

    public GridAdapter(List<MenuModel> _mMenuModels) {
        mMenuModels = _mMenuModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.menu_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuModel item = mMenuModels.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvTitle.setBackgroundColor(Color.parseColor(item.colorItem));
        //TODO: configure DownloadImageLib
    }

    @Override
    public int getItemCount() {
        return mMenuModels.size();
    }

    public MenuModel getItem(int _position) {
        return mMenuModels.get(_position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_FGI);
        }
    }
}
