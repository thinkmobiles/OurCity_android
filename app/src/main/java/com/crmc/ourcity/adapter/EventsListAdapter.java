package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.model.EventsItemModel;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<EventsItemModel> mEventsItemModels;

    public EventsListAdapter(Context context, List<EventsItemModel> _eventsItemModels) {
        this.mEventsItemModels = _eventsItemModels;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mEventsItemModels.size();
    }

    @Override
    public EventsItemModel getItem(int position) {
        return mEventsItemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_row_fc, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(getItem(position), position);

        return convertView;
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

        public void setData(EventsItemModel item, int position) {
            switch (item.catalogSizeView){
                case SMALL:
                    title.setText(item.title);
                    date.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    break;
                case MEDIUM:
                    title.setText(item.title);
                    date.setText(item.date);
                    address.setVisibility(View.GONE);
                    break;
                case FULL:
                    title.setText(item.title);
                    date.setText(item.date);
                    address.setText(item.address);
                    break;
            }
        }
    }
}
