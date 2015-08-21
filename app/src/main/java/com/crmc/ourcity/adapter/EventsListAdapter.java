package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Events> mEventsList;
    private Context mContext;

    public EventsListAdapter(Context _context, List<Events> _eventsList) {
        this.mEventsList = _eventsList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mEventsList.size();
    }

    @Override
    public Events getItem(int _position) {
        return mEventsList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fe, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));

        return _convertView;
    }

    private static class ViewHolder {
        final TextView title;
        final TextView date;
        final TextView address;
        final ImageView ivArrowEvent;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            title = (TextView) _view.findViewById(R.id.tvTitle_CF);
            date = (TextView) _view.findViewById(R.id.tvDate_CF);
            address = (TextView) _view.findViewById(R.id.tvAddress_CF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_EF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(Events _item) {

            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow_right, Image
                    .darkenColor(0.2)));

            if (TextUtils.isEmpty(_item.title)){
                title.setVisibility(View.GONE);
            } else {
                title.setText(_item.title);
            }

            if (TextUtils.isEmpty(_item.eventDateToMobileClient)){
                date.setVisibility(View.GONE);
            } else {
                date.setText(_item.eventDateToMobileClient);
            }

            if (TextUtils.isEmpty(_item.address)){
                address.setVisibility(View.GONE);
            } else {
                address.setText(_item.address);
            }
        }
    }
}
