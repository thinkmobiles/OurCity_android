package com.crmc.ourcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

    private List<VoteDetails> mVoteDetailsList;
    private String color;
    private Context mContext;
    private boolean visibleVotePercent = false;

    public VoteGridAdapter(List<VoteDetails> _optionsList, String _color, Context _context) {
        this.mVoteDetailsList = _optionsList;
        this.color = _color;
        this.mContext = _context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup _parent, int _position) {
        View view = LayoutInflater.from(_parent.getContext()).inflate(R.layout.grid_item_vf, _parent, false);
        return new ViewHolder(view);
    }

    public void setVisibleVotePercent(boolean _visible) {
        visibleVotePercent = _visible;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder _viewHolder, int _position) {
        VoteDetails voteDetails = mVoteDetailsList.get(_position);
        _viewHolder.tvVoteTitle.setText(voteDetails.optionDescription.trim());
        _viewHolder.ivVote.setImageBitmap(Image.convertBase64ToBitmap(voteDetails.image));
        _viewHolder.tvVotePercent.setText(voteDetails.votePercent + "");
        Image.setBackgroundColorView(mContext, _viewHolder.flVoteItemBoarder, R.drawable.boarder_round_red_vf);

        if (visibleVotePercent) {
            _viewHolder.ivVotePercent.setVisibility(View.VISIBLE);
            _viewHolder.ivVotePercent.setImageDrawable(Image.setDrawableImageColor(mContext, R.drawable
                    .information_corner, Image.darkenColor(0.2)));
            _viewHolder.tvVotePercent.setVisibility(View.VISIBLE);
        } else {
            _viewHolder.ivVotePercent.setVisibility(View.INVISIBLE);
            _viewHolder.tvVotePercent.setVisibility(View.INVISIBLE);
        }
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
        public ImageView ivVotePercent;
        public TextView tvVotePercent;
        public FrameLayout flVoteItemBoarder;

        public ViewHolder(View _itemView) {
            super(_itemView);
            ivVote = (ImageView) _itemView.findViewById(R.id.ivVote_VF);
            tvVoteTitle = (TextView) _itemView.findViewById(R.id.tvVoteTitle_VF);
            ivVotePercent = (ImageView) _itemView.findViewById(R.id.ivVotePercent_VF);
            tvVotePercent = (TextView) _itemView.findViewById(R.id.tvVotePercent_VF);
            flVoteItemBoarder = (FrameLayout) _itemView.findViewById(R.id.flVoteItemBoarder_VF);
        }
    }
}