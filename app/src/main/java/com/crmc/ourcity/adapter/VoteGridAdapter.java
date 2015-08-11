package com.crmc.ourcity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.rest.responce.vote.VoteDetails;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 11.08.2015.
 */
public class VoteGridAdapter extends RecyclerView.Adapter<VoteGridAdapter.ViewHolder> {

    List<VoteDetails> mVoteDetailsList;

    public VoteGridAdapter(List<VoteDetails> _optionsList) {
        this.mVoteDetailsList = _optionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup _parent, int _position) {
        View view = LayoutInflater.from(_parent.getContext()).inflate(R.layout.grid_item_vf, _parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder _viewHolder, int _position) {
        VoteDetails voteDetails = mVoteDetailsList.get(_position);
        _viewHolder.tvVoteTitle.setText(voteDetails.optionDescription);
        _viewHolder.ivVote.setImageBitmap(Image.convertBase64ToBitmap(voteDetails.image));
    }

    @Override
    public int getItemCount() {
        return mVoteDetailsList.size();
    }

    public VoteDetails getItem(int _position) {
        return mVoteDetailsList.get(_position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivVote;
        public TextView tvVoteTitle;

        public ViewHolder(View _itemView) {
            super(_itemView);
            ivVote = (ImageView) _itemView.findViewById(R.id.ivVote_VF);
            tvVoteTitle = (TextView) _itemView.findViewById(R.id.tvVoteTitle_VF);
        }
    }
}