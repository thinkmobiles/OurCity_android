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
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 10.08.2015.
 */
public class VotesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<VoteFull> mVoteFull;
    private Context mContext;

    public VotesListAdapter(Context _context, List<VoteFull> _voteFull) {
        this.mVoteFull = _voteFull;
        this.mContext = _context;
        mInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return mVoteFull.size();
    }

    @Override
    public VoteFull getItem(int _position) {
        return mVoteFull.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.listview_row_vlf, _parent, false);
            holder = new ViewHolder(_convertView, mContext);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    private class ViewHolder {
        final TextView tvTitle_VLF;
        final ImageView ivLock_VLF;
        final ImageView ivArrowEvent_VLF;
        final View view;
        final Context mContext;

        public ViewHolder(@NonNull final View _view, Context _context) {
            tvTitle_VLF = (TextView) _view.findViewById(R.id.tvTitle_VLF);
            ivLock_VLF = (ImageView) _view.findViewById(R.id.ivLock_VLF);
            ivArrowEvent_VLF = (ImageView) _view.findViewById(R.id.ivArrowEvent_VLF);
            this.view = _view;
            this.mContext = _context;
            _view.setTag(this);
        }

        public void setData(VoteFull _item) {
            ivArrowEvent_VLF.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.event_arrow, Image
                    .darkenColor(0.2)));
            tvTitle_VLF.setText(_item.surveyTitle.trim());
            if (!_item.isActive) {
                ivLock_VLF.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable.lock, Image.darkenColor
                        (0.2)));
                ivLock_VLF.setVisibility(View.VISIBLE);
            } else {
                ivLock_VLF.setVisibility(View.GONE);
            }
        }
    }
}
