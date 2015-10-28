package com.crmc.ourcity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
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
    private String color;

    public AppealsAdapter(Context _context, List<ResultObject> _resultObjects, String _color, int _amountOfVisibleTickets) {
        if (_resultObjects.size() >= _amountOfVisibleTickets) {
            switch (_amountOfVisibleTickets) {
                case 20:
                    this.resultObjects = Stream.of(_resultObjects).limit(20).collect(Collectors.toList());
                    break;
                case 50:
                    this.resultObjects = Stream.of(_resultObjects).limit(50).collect(Collectors.toList());
                    break;
                case 100:
                    this.resultObjects = Stream.of(_resultObjects).limit(100).collect(Collectors.toList());
                    break;
            }
        } else {
            this.resultObjects = _resultObjects;
        }
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
        this.color = _color;
    }

    @Override
    public int getCount() {
        return resultObjects.size();
    }

    @Override
    public ResultObject getItem(int _position) {
        return resultObjects.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
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
        holder.setData(getItem(_position));
        ImageView ivPhoto = (ImageView) _convertView.findViewById(R.id.ivImage_AF);
        if (!TextUtils.isEmpty(getItem(_position).AttachedFiles)) {
            ivPhoto.setImageBitmap(Image.convertBase64ToBitmap(getItem(_position).AttachedFiles));
        } else {
            ivPhoto.setImageBitmap(null);
        }
        return _convertView;
    }

    private class ViewHolder {
        final TextView date;
        final TextView address;
        final TextView description;
        final TextView status;
        final TextView referenceID;
        final TextView tvPhotoText_AF;
        final RelativeLayout rlImagePlaceholder;
        final LinearLayout llInfoPlaceholder;
        //        final ImageView ivPhoto;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {

            date = (TextView) _view.findViewById(R.id.tvDate_AF);
            address = (TextView) _view.findViewById(R.id.tvAddress_AF);
            description = (TextView) _view.findViewById(R.id.tvDescription_AF);
            status = (TextView) _view.findViewById(R.id.tvStat_AF);
            tvPhotoText_AF = (TextView) _view.findViewById(R.id.tvPhotoText_AF);
            referenceID = (TextView) _view.findViewById(R.id.tvReferenceID_AF);
            rlImagePlaceholder = (RelativeLayout) _view.findViewById(R.id.rlImagePlaceholder_ALF);
            llInfoPlaceholder = (LinearLayout) _view.findViewById(R.id.llInformationPlaceholder_ALF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        @SuppressLint("SetTextI18n")
        public void setData(ResultObject _item) {
            Image.setBoarderBackgroundColorArray(mContext, color, 2, 5, "#ffffff",
                    new View[]{rlImagePlaceholder, llInfoPlaceholder});

            if (!TextUtils.isEmpty(_item.AttachedFiles)) {
                tvPhotoText_AF.setText("");
            } else {
                tvPhotoText_AF.setText("אין תמונה");
            }

            if (!TextUtils.isEmpty(_item.DateCreatedClient)) {
                date.setText(_item.DateCreatedClient.trim());
            }
            if (!TextUtils.isEmpty(_item.Location.StreetName)) {
                address.setText(_item.Location.HouseNumber.trim() + " " + _item.Location.StreetName.trim());
            }
            if (!TextUtils.isEmpty(_item.Description)) {
                description.setText(_item.Description.trim());
            }
            if (!TextUtils.isEmpty(_item.Status)) {
                status.setText(_item.Status);
            }
            if (!TextUtils.isEmpty(_item.ReferenceID)) {
                referenceID.setText(_item.ReferenceID.trim());
            }
        }
    }
}
