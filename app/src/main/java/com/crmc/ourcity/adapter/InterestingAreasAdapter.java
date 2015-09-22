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
import com.crmc.ourcity.rest.responce.interestAreas.FullInterestArea;
import com.crmc.ourcity.rest.responce.interestAreas.InterestingArea;
import com.crmc.ourcity.rest.responce.interestAreas.ResidentResponse;

import java.util.List;

/**
 * Created by andrei on 17.09.15.
 */
public class InterestingAreasAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ResidentResponse residentResponse;
    private List<InterestingArea> interestingAreasList;

    public InterestingAreasAdapter(Context _context, ResidentResponse _residentResponse) {
        this.mInflater = LayoutInflater.from(_context);
        this.residentResponse = _residentResponse;


    }

    @Override
    public int getCount() {
        return residentResponse.interestAreasModelsBool.size();
    }

    @Override
    public FullInterestArea getItem(int _position) {
        return residentResponse.interestAreasModelsBool.get(_position);
    }


    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_dialog_ia, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position),_position);

        return _convertView;
    }

    private class ViewHolder implements CompoundButton.OnCheckedChangeListener {
        final TextView tvInterestingAreaTitle;
        final CheckBox cbInterestingArea;
        final View view;
        private int position;

        public ViewHolder(@NonNull final View _view) {
            tvInterestingAreaTitle = (TextView) _view.findViewById(R.id.tvInterestingAreaTitle_DIA);
            cbInterestingArea = (CheckBox) _view.findViewById(R.id.cbInterestingArea_DIA);
            cbInterestingArea.setOnCheckedChangeListener(this);
            this.view = _view;
            _view.setTag(this);
        }

        public void setData(FullInterestArea _item, int _position) {
            this.position = _position;
            tvInterestingAreaTitle.setText(_item.Key.interestAreaName);
            cbInterestingArea.setChecked(_item.Value);
        }

        @Override
        public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {
            getItem(position).Value = _isChecked;
        }
    }

    public ResidentResponse getResidentResponse() {
        return this.residentResponse;
    }
}
