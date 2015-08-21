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
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

import java.util.List;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Events> mEventsList;
    private Context mContext;
    private OnListItemActionListener mOnListItemActionListener;

    public EventsListAdapter(Context _context, List<Events> _eventsList, OnListItemActionListener
            _onListItemActionListener) {
        this.mOnListItemActionListener = _onListItemActionListener;
        this.mEventsList = _eventsList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mEventsList.size();
    }

    @Override
    public Events getItem(int _position) {
        return mEventsList.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fe, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position), _position);

        return _convertView;
    }

    private class ViewHolder implements View.OnClickListener {
        final TextView title;
        final TextView date;
        final TextView address;
        final ImageView ivArrowEvent;
        final ImageView ivCallSkype;
        final ImageView ivSendMail;
        final ImageView ivLink;
        final View view;
        final Context mContext;
        private int position;

        public ViewHolder(@NonNull final View _view, Context _context) {
            title = (TextView) _view.findViewById(R.id.tvTitle_EIF);
            date = (TextView) _view.findViewById(R.id.tvDate_EIF);
            address = (TextView) _view.findViewById(R.id.tvAddress_EIF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_EF);
            ivCallSkype = (ImageView) _view.findViewById(R.id.ivCallSkype_EF);
            ivSendMail = (ImageView) _view.findViewById(R.id.ivSendMail_EF);
            ivLink = (ImageView) _view.findViewById(R.id.ivLink_EF);
            ivCallSkype.setOnClickListener(this);
            ivSendMail.setOnClickListener(this);
            ivLink.setOnClickListener(this);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(Events _item, int _position) {
            this.position = _position;
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow_right, Image
                    .darkenColor(0.2)));
            if (!TextUtils.isEmpty(getItem(position).phone)){
                ivCallSkype.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone, Image
                        .darkenColor(0.2)));
            } else {
                ivCallSkype.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).email)){
                ivSendMail.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.mail, Image
                        .darkenColor(0.2)));
            } else {
                ivSendMail.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).link)){
                ivLink.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.link, Image
                        .darkenColor(0.2)));
            } else {
                ivLink.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(_item.title)){
                title.setVisibility(View.GONE);
            } else {
                title.setText(_item.title);
            }
            if (TextUtils.isEmpty(_item.eventDateToMobileClient)){
                date.setVisibility(View.GONE);
            } else {
                date.setText(_item.eventDateToMobileClient);
            }
            if (TextUtils.isEmpty(_item.address)){
                address.setVisibility(View.GONE);
            } else {
                address.setText(_item.address);
            }
        }

        @Override
        public void onClick(View _view) {
            switch (_view.getId()) {
                case R.id.ivCallSkype_EF:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentSkype(getItem(position)
                                .phone), mContext.getResources().getString(R.string.call_skype_hint)));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_skype_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivSendMail_EF:
                    try {
                        mContext.startActivity(Intent.createChooser(IntentUtils.getIntentMail(getItem(position)
                                .email), mContext.getResources().getString(R.string.send_mail_hint)));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivLink_EF:
                    mOnListItemActionListener.onEventsClickLinkAction(getItem(position).link);
                    break;
            }
        }
    }
}
