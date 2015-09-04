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
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 25.08.2015.
 */
public class TripsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MapTrips> mMapTrips;
    private Context mContext;

    public TripsListAdapter(Context _context, List<MapTrips> _mMapTripses) {
        this.mMapTrips = _mMapTripses;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mMapTrips.size();
    }

    @Override
    public MapTrips getItem(int _position) {
        return mMapTrips.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_ft, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    private class ViewHolder{
        final TextView tvTripName_TF;
        final TextView tvDescription_TF;
        final ImageView ivArrowEvent;
        View view;

        public ViewHolder(@NonNull final View _view) {
            tvTripName_TF = (TextView) _view.findViewById(R.id.tvTripName_TF);
            tvDescription_TF = (TextView) _view.findViewById(R.id.tvDescription_TF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_TF);
            this.view = _view;
            view.setTag(this);
        }

        public void setData(MapTrips _item) {
            tvTripName_TF.setText(_item.tripName.trim());
            tvDescription_TF.setText(_item.description.trim());
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable
                    .event_arrow, Image.darkenColor(0.2)));
        }
    }
}
