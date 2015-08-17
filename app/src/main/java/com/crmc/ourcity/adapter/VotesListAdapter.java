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

import java.util.List;

/**
 * Created by SetKrul on 10.08.2015.
 */
public class VotesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<VoteFull> mVoteFull;

    public VotesListAdapter(Context _context, List<VoteFull> _voteFull) {
        this.mVoteFull = _voteFull;
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
            _convertView = mInflater.inflate(R.layout.listview_row_dialog_cv, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    private class ViewHolder {
        final TextView tvVoteTitle;
        final ImageView ivVoteStatus;
        final View view;

        public ViewHolder(@NonNull final View _view) {
            tvVoteTitle = (TextView) _view.findViewById(R.id.tvVoteTitle_DCV);
            ivVoteStatus = (ImageView) _view.findViewById(R.id.ivVoteStatus_DCV);
            this.view = _view;
            _view.setTag(this);
        }

        public void setData(VoteFull _item) {
            tvVoteTitle.setText(_item.surveyTitle);
            if (_item.isActive) {
                ivVoteStatus.setImageResource(R.drawable.new_menu_dvar_resh_hyer);
            } else {
                ivVoteStatus.setImageResource(R.drawable.new_menu_cherom_red);
            }
        }
    }
}
