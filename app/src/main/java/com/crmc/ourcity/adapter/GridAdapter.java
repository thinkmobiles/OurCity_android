package com.crmc.ourcity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.util.List;

/**
 * Created by podo on 05.08.15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private List<MenuModel> menuModels;


    public GridAdapter(List<MenuModel> _menuModels) {
        menuModels = _menuModels;
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
        MenuModel item = menuModels.get(position);
        holder.title.setText(item.title);
        holder.imgThumbnail.setImageResource(R.drawable.new_menu_general_report);
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    public MenuModel getItem(int _position) {
        return menuModels.get(_position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
