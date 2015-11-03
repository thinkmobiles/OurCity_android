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
import com.crmc.ourcity.model.MapMarker;

import java.util.List;

public class MarkersListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<MapMarker> mMapMarkers;

    public MarkersListAdapter(Context _context, List<MapMarker> _mapMarkers) {
        this.mMapMarkers = _mapMarkers;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mMapMarkers.size();
    }

    final public List<MapMarker> getResult() {
        return mMapMarkers;
    }

    @Override
    public MapMarker getItem(int _position) {
        return mMapMarkers.get(_position);
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

        public void setData(MapMarker _item, int _position) {
            this.position = _position;
            tvCategoryMarkers.setText(_item.title.trim());
            cbCategoryMarkersVisible.setChecked(_item.visible);
        }

        @Override
        public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {
            getItem(position).visible = _isChecked;
        }
    }
}
