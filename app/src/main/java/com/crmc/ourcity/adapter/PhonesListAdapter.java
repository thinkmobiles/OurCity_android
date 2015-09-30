package com.crmc.ourcity.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class PhonesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Phones> mPhonesList;
    private Context mContext;
    private OnListItemActionListener mOnListItemActionListener;

    public PhonesListAdapter(Context _context, List<Phones> _phonesList, OnListItemActionListener
            _onListItemActionListener) {
        this.mOnListItemActionListener = _onListItemActionListener;
        this.mPhonesList = _phonesList;
        this.mContext = _context;
        this.mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mPhonesList.size();
    }

    @Override
    public Phones getItem(int _position) {
        return mPhonesList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fp, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);
        return _convertView;
    }

    private class ViewHolder implements View.OnClickListener {
        final TextView tvNameInstitution;
        final ImageView ivCallSkype;
        final ImageView ivSendMail;
        View view;
        private int position;

        public ViewHolder(@NonNull final View _view) {
            tvNameInstitution = (TextView) _view.findViewById(R.id.tvNameInstitution_FP);
            ivCallSkype = (ImageView) _view.findViewById(R.id.btnCallSkype_FP);
            ivSendMail = (ImageView) _view.findViewById(R.id.btnSendMail_FP);
            ivCallSkype.setOnClickListener(this);
            ivSendMail.setOnClickListener(this);
            this.view = _view;
            view.setTag(this);
        }

        public void setData(Phones _item, int _position) {
            this.position = _position;
            tvNameInstitution.setText(_item.entityName.trim());
            if (!TextUtils.isEmpty(getItem(position).phoneNumber)) {
                ivCallSkype.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone2,
                        Image.darkenColor(0.2)));
            } else {
                ivCallSkype.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).emailAddress)) {
                ivSendMail.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.mail2,
                        Image.darkenColor(0.2)));
            } else {
                ivSendMail.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View _view) {
            switch (_view.getId()) {
                case R.id.btnCallSkype_FP:
                        try {
                            mOnListItemActionListener.onActionCall(getItem(position).phoneNumber);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_call_client),
                                    Toast.LENGTH_SHORT).show();
                        }
                    break;
                case R.id.btnSendMail_FP:
                        try {
                            mOnListItemActionListener.onActionMail(getItem(position).emailAddress);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
                                    Toast.LENGTH_SHORT).show();
                        }
                    break;
            }
        }
    }
}
