package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class LinksListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Events> mEventsList;
    private Context mContext;

    public LinksListAdapter(Context _context, List<Events> _eventsList) {
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
            _convertView = mInflater.inflate(R.layout.listview_row_fll, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    private class ViewHolder {
        final TextView title;
        final View view;
        final ImageView ivArrowEvent;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            title = (TextView) _view.findViewById(R.id.tvTitle_LLF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_LLF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(Events _item) {
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow,
                    Image.darkenColor(0.2)));
                title.setText(_item.title.trim());
        }
    }
}
