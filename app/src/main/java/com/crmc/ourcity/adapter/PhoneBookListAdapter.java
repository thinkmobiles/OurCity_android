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
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.events.PhoneBook;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 27.08.2015.
 */
public class PhoneBookListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<PhoneBook> mPhoneBooksList;
    private Context mContext;
    private OnListItemActionListener mOnListItemActionListener;

    public PhoneBookListAdapter(Context _context, List<PhoneBook> _phoneBookList, OnListItemActionListener
            _onListItemActionListener) {
        this.mOnListItemActionListener = _onListItemActionListener;
        this.mPhoneBooksList = _phoneBookList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mPhoneBooksList.size();
    }

    @Override
    public PhoneBook getItem(int _position) {
        return mPhoneBooksList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fpb, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));

        return _convertView;
    }

    private class ViewHolder {
        final TextView tvCategory_PBF;
        final ImageView ivArrowPhoneBook_PBF;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            tvCategory_PBF = (TextView) _view.findViewById(R.id.tvCategory_PBF);
            ivArrowPhoneBook_PBF = (ImageView) _view.findViewById(R.id.ivArrowPhoneBook_PBF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(PhoneBook _item) {
            ivArrowPhoneBook_PBF.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow_right,
                    Image.darkenColor(0.2)));
            tvCategory_PBF.setText(_item.categoryName.trim());
        }
    }
}

