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
import com.crmc.ourcity.model.MapFilterSelected;

import java.util.List;

/**
 * Created by SetKrul on 04.08.2015.
 */
public class MapFilterListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<MapFilterSelected> mapFilterSelected;

    public MapFilterListAdapter(Context _context, List<MapFilterSelected> _mapFilterSelected) {
        this.mapFilterSelected = _mapFilterSelected;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mapFilterSelected.size();
    }

    final public List<MapFilterSelected> getResult() {
        return mapFilterSelected;
    }

    @Override
    public MapFilterSelected getItem(int _position) {
        return mapFilterSelected.get(_position);
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
        final TextView tvCategoryMarkers_DMF;
        final CheckBox cbCategoryMarkersVisible_DMF;
        final View view;
        private int position;

        public ViewHolder(@NonNull final View _view) {
            tvCategoryMarkers_DMF = (TextView) _view.findViewById(R.id.tvCategoryMarkers_DMF);
            cbCategoryMarkersVisible_DMF = (CheckBox) _view.findViewById(R.id.cbCategoryMarkersVisible_DMF);
            cbCategoryMarkersVisible_DMF.setOnCheckedChangeListener(this);
            this.view = _view;
            _view.setTag(this);
        }

        public void setData(MapFilterSelected _item, int _position) {
            this.position = _position;
            tvCategoryMarkers_DMF.setText(_item.title);
            cbCategoryMarkersVisible_DMF.setChecked(_item.visible);
        }

        @Override
        public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {
            getItem(position).visible = _isChecked;
        }
    }
}
