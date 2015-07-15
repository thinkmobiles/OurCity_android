package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.model.CatalogItemModel;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class CatalogListAdapter extends BaseAdapter implements OnItemClickListener {

    private LayoutInflater mInflater;
    private List<CatalogItemModel> catalogItemModels;

    public CatalogListAdapter(Context context, List<CatalogItemModel> catalogItemModels) {
        this.catalogItemModels = catalogItemModels;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return catalogItemModels.size();
    }

    @Override
    public CatalogItemModel getItem(int position) {
        return catalogItemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cf_listview_row, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(getItem(position), position);

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CatalogItemModel item = getItem(position);
        switch (item.itemStatus) {
            case ITEM:
                break;
            case MAIL:
                break;
            case LINK:
                break;
            case FALSE:
                break;
        }
    }

    private static class ViewHolder {
        final TextView title;
        final TextView date;
        final TextView address;
        final View view;

        public ViewHolder(@NonNull final View view) {
            title = (TextView) view.findViewById(R.id.tvTitle_CF);
            date = (TextView) view.findViewById(R.id.tvDate_CF);
            address = (TextView) view.findViewById(R.id.tvAddress_CF);
            this.view = view;
            view.setTag(this);
        }

        public void setData(CatalogItemModel item, int position) {
            if (!TextUtils.isEmpty(item.title)){
                title.setText(item.title);
            } else {
                title.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.date)){
                date.setText(item.date);
            } else {
                date.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.address)){
                address.setText(item.address);
            } else {
                address.setVisibility(View.GONE);
            }
//            if (position % 2 == 0) {
//                view.setBackgroundResource(R.drawable.f_pf_list_row_backgroundcolor);
//            } else {
//                view.setBackgroundResource(R.drawable.f_pf_list_row_background_alternate);
//            }
        }
    }
}
