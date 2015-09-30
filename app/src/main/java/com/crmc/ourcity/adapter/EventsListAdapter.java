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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.utils.Image;

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
        final ImageView ivCall;
        final ImageView ivSendMail;
        final ImageView ivLink;
        final View view;
        final LinearLayout llEventsControl_EF;
        final Context mContext;
        private int position;

        public ViewHolder(@NonNull final View _view, Context _context) {
            llEventsControl_EF = (LinearLayout) _view.findViewById(R.id.llEventsControl_EF);
            title = (TextView) _view.findViewById(R.id.tvTitle_EIF);
            date = (TextView) _view.findViewById(R.id.tvDate_EIF);
            address = (TextView) _view.findViewById(R.id.tvAddress_EIF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_EF);
            ivCall = (ImageView) _view.findViewById(R.id.ivCallSkype_EF);
            ivSendMail = (ImageView) _view.findViewById(R.id.ivSendMail_EF);
            ivLink = (ImageView) _view.findViewById(R.id.ivLink_EF);
            ivCall.setOnClickListener(this);
            ivSendMail.setOnClickListener(this);
            ivLink.setOnClickListener(this);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(Events _item, int _position) {
            this.position = _position;
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow, Image
                    .darkenColor(0.2)));
            if (!TextUtils.isEmpty(getItem(position).phone)) {
                ivCall.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone,
                        Image.darkenColor(0.2)));
                ivCall.setVisibility(View.VISIBLE);
            } else {
                ivCall.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).email)) {
                ivSendMail.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.mail, Image.darkenColor
                        (0.2)));
                ivSendMail.setVisibility(View.VISIBLE);
            } else {
                ivSendMail.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(getItem(position).link)) {
                ivLink.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.link, Image.darkenColor(0.2)));
                ivLink.setVisibility(View.VISIBLE);
            } else {
                ivLink.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(_item.title)) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(_item.title.trim());
            }
            if (TextUtils.isEmpty(_item.eventDateToMobileClient)) {
                date.setVisibility(View.GONE);
            } else {
                date.setVisibility(View.VISIBLE);
                date.setText(_item.eventDateToMobileClient.trim());
            }
            if (TextUtils.isEmpty(_item.address)) {
                address.setVisibility(View.GONE);
            } else {
                address.setVisibility(View.VISIBLE);
                address.setText(_item.address.trim());
            }
        }

        @Override
        public void onClick(View _view) {
            switch (_view.getId()) {
                case R.id.ivCallSkype_EF:
                    try {
                        mOnListItemActionListener.onActionCall(getItem(position).phone);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_call_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivSendMail_EF:
                    try {
                        mOnListItemActionListener.onActionMail(getItem(position).email);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivLink_EF:
                    mOnListItemActionListener.onEventsClickLinkAction(getItem(position).link, getItem(position).title);
                    break;
            }
        }
    }
}
