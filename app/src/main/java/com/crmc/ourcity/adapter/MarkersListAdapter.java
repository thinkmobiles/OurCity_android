package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.model.Marker;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public class MarkersListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<Marker> mMarkers;

    public MarkersListAdapter(Context _context, List<Marker> _markers) {
        this.mMarkers = _markers;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mMarkers.size();
    }

    final public List<Marker> getResult() {
        return mMarkers;
    }

    @Override
    public Marker getItem(int _position) {
        return mMarkers.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_dialog_mf, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position),_position);

        return _convertView;
    }

    private class ViewHolder implements CompoundButton.OnCheckedChangeListener {
        final TextView tvCategoryMarkers;
        final CheckBox cbCategoryMarkersVisible;
        final View view;
        private int position;

        public ViewHolder(@NonNull final View _view) {
            tvCategoryMarkers = (TextView) _view.findViewById(R.id.tvCategoryMarkers_DMF);
            cbCategoryMarkersVisible = (CheckBox) _view.findViewById(R.id.cbCategoryMarkersVisible_DMF);
            cbCategoryMarkersVisible.setOnCheckedChangeListener(this);
            this.view = _view;
            _view.setTag(this);
        }

        public void setData(Marker _item, int _position) {
            this.position = _position;
            tvCategoryMarkers.setText(_item.title);
            cbCategoryMarkersVisible.setChecked(_item.visible);
        }

        @Override
        public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {
            getItem(position).visible = _isChecked;
        }
    }
}