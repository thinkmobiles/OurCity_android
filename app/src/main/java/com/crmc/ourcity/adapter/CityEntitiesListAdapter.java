package com.crmc.ourcity.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.List;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntitiesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<CityEntities> mCityEntities;
    private Context mContext;

    public CityEntitiesListAdapter(Context _context, List<CityEntities> _cityEntitiesList) {
        this.mCityEntities = _cityEntitiesList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mCityEntities.size();
    }

    @Override
    public CityEntities getItem(int _position) {
        return mCityEntities.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fce, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);
        return _convertView;
    }

    private class ViewHolder implements View.OnClickListener {
        final TextView title;
        final ImageView ivArrowEvent;
        final ImageView ivCall;
        final ImageView ivSendMail;
        final View view;
        final Context mContext;
        private int position;

        public ViewHolder(@NonNull final View _view, Context _context) {
            title = (TextView) _view.findViewById(R.id.tvEntityName_CEF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowCityEntities_CEF);
            ivCall = (ImageView) _view.findViewById(R.id.ivCall_CEF);
            ivSendMail = (ImageView) _view.findViewById(R.id.ivMail_CEF);
            ivCall.setOnClickListener(this);
            ivSendMail.setOnClickListener(this);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(CityEntities _item, int _position) {
            this.position = _position;
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow_right, Image
                    .darkenColor(0.2)));
            if (!TextUtils.isEmpty(getItem(position).phoneNumber)) {
                ivCall.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone,
                        Image.darkenColor(0.2)));
            } else {
                ivCall.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).emailAddress)) {
                ivSendMail.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.mail, Image.darkenColor
                        (0.2)));
            } else {
                ivSendMail.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(_item.entityName)) {
                title.setVisibility(View.GONE);
            } else {
                title.setText(_item.entityName);
            }
        }

        @Override
        public void onClick(View _view) {
            switch (_view.getId()) {
                case R.id.ivCall_CEF:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentCall(getItem(position)
                                .phoneNumber), mContext.getResources().getString(R.string.call_hint)));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_call_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivMail_CEF:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentMail(getItem(position)
                                .emailAddress), mContext.getResources().getString(R.string.send_mail_hint)));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
