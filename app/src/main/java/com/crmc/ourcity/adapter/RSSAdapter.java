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
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.model.rss.RSSEntry;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by podo on 01.09.15.
 */
public class RSSAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<RSSEntry> mRSSEnties;
    private Context mContext;
    private OnListItemActionListener mOnListItemActionListener;

    public RSSAdapter(final Context _context, final List<RSSEntry> _rssEntries, OnListItemActionListener
            _onListItemActionListener) {
        mContext = _context;
        mInflater = LayoutInflater.from(mContext);
        mRSSEnties = _rssEntries;
        mOnListItemActionListener = _onListItemActionListener;
    }

    @Override
    public int getCount() {
        return mRSSEnties.size();
    }

    @Override
    public RSSEntry getItem(int _position) {
        return mRSSEnties.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_fr, _parent, false);
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
        final LinearLayout llDate;
//        final ImageView ivLink;
        final ImageView ivArrowEvent;
        final View view;
        final Context mContext;
        int position;

        ViewHolder(@NonNull final View _view, Context _context) {
            this.title = (TextView) _view.findViewById(R.id.tvTitle_RssFrgmt);
            this.date = (TextView) _view.findViewById(R.id.tvDate_Text_RssFrgmt);
            this.llDate = (LinearLayout) _view.findViewById(R.id.llDate_RssFrgmt);
//            this.ivLink = (ImageView) _view.findViewById(R.id.ivLink_RssFrgmt);
//            this.ivLink.setOnClickListener(this);
            this.ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowEvent_RssFrgmt);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        @SuppressLint("SetTextI18n")
        void setData(RSSEntry _entry, int _position) {
            this.position = _position;
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow, Image
                    .darkenColor(0.2)));

            if (TextUtils.isEmpty(_entry.getTitle())) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(_entry.getTitle().trim());
            }

            if (TextUtils.isEmpty(_entry.getPubDate())) {
                date.setVisibility(View.GONE);
                llDate.setVisibility(View.GONE);
            } else {
                date.setVisibility(View.VISIBLE);
                llDate.setVisibility(View.VISIBLE);
                date.setText(getDateTime(_entry.getPubDate().trim()));
            }

//            if (!TextUtils.isEmpty(_entry.getLink())) {
//                ivLink.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.link, Image
//                        .darkenColor(0.2)));
//                ivLink.setVisibility(View.VISIBLE);
//            } else {
//                ivLink.setVisibility(View.GONE);
//            }
        }

        @SuppressLint("SimpleDateFormat")
        public String getDateTime(String _data) {
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy " +
//                    "HH:mm:ss");
//            Date date = null;
//            try {
//                date = formatter.parse(_data);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            DateFormat dt1 = new SimpleDateFormat("EEE, dd.mm.yyyy HH:mm");
//            return dt1.format(date) + "";
////            return formatter.format(date);
            if (!TextUtils.isEmpty(_data)){
                return _data.substring(0, _data.length() - 3);
            } else {
                return "";
            }
        }

        @Override
        public void onClick(View v) {
            mOnListItemActionListener.onEventsClickLinkAction(getItem(position).getLink(), getItem(position).getTitle());
        }
    }
}
