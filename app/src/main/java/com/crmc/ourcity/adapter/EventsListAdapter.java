package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.events.Events;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Events> mEventsItemModels;

    public EventsListAdapter(Context _context, List<Events> _eventsItemModels) {
        this.mEventsItemModels = _eventsItemModels;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mEventsItemModels.size();
    }

    @Override
    public Events getItem(int _position) {
        return mEventsItemModels.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fc, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);

        return _convertView;
    }

    private static class ViewHolder {
        final TextView title;
        final TextView date;
        final TextView address;
        final View view;

        public ViewHolder(@NonNull final View _view) {
            title = (TextView) _view.findViewById(R.id.tvTitle_CF);
            date = (TextView) _view.findViewById(R.id.tvDate_CF);
            address = (TextView) _view.findViewById(R.id.tvAddress_CF);
            this.view = _view;
            _view.setTag(this);
        }

        public void setData(Events _item, int _position) {
//            switch (_item.mEventsSizeView){
//                case SMALL:
//                    title.setText(_item.title);
//                    date.setVisibility(View.GONE);
//                    address.setVisibility(View.GONE);
//                    break;
//                case MEDIUM:
//                    title.setText(_item.title);
//                    date.setText(_item.date);
//                    address.setVisibility(View.GONE);
//                    break;
//                case FULL:
//                    title.setText(_item.title);
//                    date.setText(_item.date);
//                    address.setText(_item.address);
//                    break;
//            }
        }
    }
}
