package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.utils.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntitiesListAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater mInflater;
    private List<CityEntities> mCityEntities;
    private List<CityEntities> filterableList;
    private Context mContext;
    private EntitiesFilter mFilter;
    private OnListItemActionListener mOnListItemActionListener;

    public CityEntitiesListAdapter(Context _context, List<CityEntities> _cityEntitiesList, OnListItemActionListener
            _onListItemActionListener) {
        this.mOnListItemActionListener = _onListItemActionListener;
        filterableList = new ArrayList<>();
        filterableList.addAll(_cityEntitiesList);
        this.mCityEntities = _cityEntitiesList;
        this.mInflater = LayoutInflater.from(_context);
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return filterableList.size();
    }

    @Override
    public CityEntities getItem(int _position) {
        return filterableList.get(_position);
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

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new EntitiesFilter();
        }
        return mFilter;
    }


    private class EntitiesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CityEntities> prospects;
            try {
                prospects = getFilteredList(constraint.toString());
            } catch (IllegalArgumentException ignored) {
                prospects = mCityEntities;
            }
            results.count = prospects.size();
            results.values = prospects;
            return results;
        }

        private List<CityEntities> getFilteredList(String text) {
            String search = null;
            filterableList.clear();
            if (text.isEmpty()) {
                filterableList = new ArrayList<>();
                filterableList.addAll(mCityEntities);
                return filterableList;
            } else {
                for (CityEntities wp : mCityEntities) {
                    String name = wp.entityName;
                    String post = wp.entityPost;
//                    if (!TextUtils.isEmpty(name)){
//                        search = name;
//                    }
//                    if (!TextUtils.isEmpty(post)){
//                        search += " " + post;
//                    }
                    if (!TextUtils.isEmpty(text)) {
                        if (name.contains(text) || post.contains((text))){
                            filterableList.add(wp);
                        }
                    }
                }
                return filterableList;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                filterableList = (List<CityEntities>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder implements View.OnClickListener {
        final TextView title;
        final TextView post;
        final ImageView ivArrowEvent;
        //        final ImageView ivCall;
//        final ImageView ivSendMail;
//        final ImageView ivCall_Mobile;
        final View view;
        final Context mContext;
        private int position;

        public ViewHolder(@NonNull final View _view, Context _context) {
            title = (TextView) _view.findViewById(R.id.tvEntityName_CEF);
            post = (TextView) _view.findViewById(R.id.tvPost_CEF);
            ivArrowEvent = (ImageView) _view.findViewById(R.id.ivArrowCityEntities_CEF);
//            ivCall = (ImageView) _view.findViewById(R.id.ivCall_CEF);
//            ivSendMail = (ImageView) _view.findViewById(R.id.ivMail_CEF);
//            ivCall_Mobile = (ImageView) _view.findViewById(R.id.ivCall_Mobile_CEF);
//            ivCall.setOnClickListener(this);
//            ivSendMail.setOnClickListener(this);
//            ivCall_Mobile.setOnClickListener(this);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(CityEntities _item, int _position) {
            this.position = _position;
            ivArrowEvent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow, Image
                    .darkenColor(0.2)));
//            if (!TextUtils.isEmpty(_item.phoneNumber)) {
//                ivCall.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone2,
//                        Image.darkenColor(0.2)));
//
//                ivCall.setVisibility(View.VISIBLE);
//            } else {
//                ivCall.setVisibility(View.GONE);
//            }
//            if (!TextUtils.isEmpty(_item.emailAddress)) {
//                ivSendMail.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.mail, Image.darkenColor
//                        (0.2)));
//
//                ivSendMail.setVisibility(View.VISIBLE);
//            } else {
//                ivSendMail.setVisibility(View.GONE);
//            }
//            if (!TextUtils.isEmpty(_item.mobileNumber)) {
//                ivCall_Mobile.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.phone,
//                        Image.darkenColor(0.2)));
//                ivCall_Mobile.setVisibility(View.VISIBLE);
//            } else {
//                ivCall_Mobile.setVisibility(View.GONE);
//            }
            if (TextUtils.isEmpty(_item.entityName)) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(_item.entityName.trim());
            }
            if (TextUtils.isEmpty(_item.entityPost)) {
                post.setVisibility(View.GONE);
            } else {
                post.setVisibility(View.VISIBLE);
                post.setText(_item.entityPost.trim());
            }
        }

        @Override
        public void onClick(View _view) {
//            switch (_view.getId()) {
//                case R.id.ivCall_CEF:
//                    try {
//                        mOnListItemActionListener.onActionCall(getItem(position).phoneNumber);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_call_client),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case R.id.ivMail_CEF:
//                    try {
//                        mOnListItemActionListener.onActionMail(getItem(position).emailAddress);
//                    } catch (android.content.ActivityNotFoundException ex) {
//                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_mail_client),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case R.id.ivCall_Mobile_CEF:
//                    try {
//                        mOnListItemActionListener.onActionCall(getItem(position).mobileNumber);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(mContext, mContext.getResources().getString(R.string.app_no_call_client),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
        }
    }
}
