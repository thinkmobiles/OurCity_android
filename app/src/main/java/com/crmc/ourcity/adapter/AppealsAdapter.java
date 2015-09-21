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
import com.crmc.ourcity.rest.responce.appeals.ResultObject;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by podo on 04.09.15.
 */
public class AppealsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ResultObject> resultObjects;
    private Context mContext;

    public AppealsAdapter(Context _context, List<ResultObject> _resultObjects) {
        this.resultObjects = _resultObjects;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return resultObjects.size();
    }

    @Override
    public ResultObject getItem(int position) {
        return resultObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_appeals_af, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);
        return _convertView;
    }

    private class ViewHolder {
        final TextView date;
        final TextView address;
        final TextView description;
        final TextView status;
        final TextView referenceID;
        final TextView tvPhotoText_AF;
        final ImageView ivPhoto;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            date = (TextView) _view.findViewById(R.id.tvDate_AF);
            address = (TextView) _view.findViewById(R.id.tvAddress_AF);
            description = (TextView) _view.findViewById(R.id.tvDescription_AF);
            status = (TextView) _view.findViewById(R.id.tvStat_AF);
            tvPhotoText_AF = (TextView) _view.findViewById(R.id.tvPhotoText_AF);
            referenceID = (TextView) _view.findViewById(R.id.tvReferenceID_AF);
            ivPhoto = (ImageView) _view.findViewById(R.id.ivImage_AF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(ResultObject _item, int _position) {
            if (_item.AttachedFilesBitmap == null) {
                if (!TextUtils.isEmpty(_item.AttachedFiles)) {
                    _item.AttachedFilesBitmap = Image.convertBase64ToBitmap(_item.AttachedFiles);
                    ivPhoto.setImageBitmap(_item.AttachedFilesBitmap);
                    tvPhotoText_AF.setText("");
                }
            } else {
                ivPhoto.setImageBitmap(_item.AttachedFilesBitmap);
                tvPhotoText_AF.setText("");
            }

            if (!TextUtils.isEmpty(_item.DateCreatedClient)) {
                date.setText(_item.DateCreatedClient);
            }
            if (!TextUtils.isEmpty(_item.Location.StreetName)) {
                address.setText(_item.Location.HouseNumber + " " + _item.Location.StreetName);
            }
            if (!TextUtils.isEmpty(_item.Description)) {
                description.setText(_item.Description);
            }
            if (!TextUtils.isEmpty(_item.Status)) {
                status.setText(_item.Status);
            }
            if (!TextUtils.isEmpty(_item.ReferenceID)) {
                referenceID.setText(_item.ReferenceID);
            }

        }
    }
}
