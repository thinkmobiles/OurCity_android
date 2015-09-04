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
import com.crmc.ourcity.rest.responce.events.MassageToResident;

import java.util.List;

/**
 * Created by SetKrul on 28.08.2015.
 */
public class MassageToResidentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MassageToResident> mMassageToResidentList;
    private Context mContext;

    public MassageToResidentAdapter(Context _context, List<MassageToResident> _massageToResidentsList) {
        this.mMassageToResidentList = _massageToResidentsList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mMassageToResidentList.size();
    }

    @Override
    public MassageToResident getItem(int _position) {
        return mMassageToResidentList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fmtr, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));

        return _convertView;
    }

    private class ViewHolder {
        final TextView tvMessage_MTRF;
        final ImageView ivArrowMessageToResident_MTRF;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            tvMessage_MTRF = (TextView) _view.findViewById(R.id.tvMessage_MTRF);
            ivArrowMessageToResident_MTRF = (ImageView) _view.findViewById(R.id.ivArrowMessageToResident_MTRF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(MassageToResident _item) {
//            ivArrowMessageToResident_MTRF.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow,
//                    Image.darkenColor(0.2)));
            tvMessage_MTRF.setText(_item.message);
        }
    }
}
