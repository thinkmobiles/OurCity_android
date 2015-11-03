package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.VotesListAdapter;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.rest.responce.vote.VoteFull;

import java.util.ArrayList;

public class VoteChoiceDialog extends BaseFourStatesFragment implements AdapterView.OnItemClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private CallBackWithData mCallback;
    private ArrayList<VoteFull> mVoteFulls;
    private ListView lvChoiceVote;
    private VotesListAdapter mAdapter;

    public VoteChoiceDialog() {
        super();
    }

    public static VoteChoiceDialog newInstance(ArrayList<VoteFull> _mVoteFull) {
        VoteChoiceDialog mVoteChoiceDialog = new VoteChoiceDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONFIGURATION_KEY, _mVoteFull);
        mVoteChoiceDialog.setArguments(args);
        return mVoteChoiceDialog;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mVoteFulls = getArguments().getParcelableArrayList(CONFIGURATION_KEY);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mCallback = (CallBackWithData) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListenerWithData");
        }
    }

    @Override
    protected void initViews() {
        lvChoiceVote = findView(R.id.lvChoiceVote_DCV);
        mAdapter = new VotesListAdapter(getActivity(), mVoteFulls);
        lvChoiceVote.setAdapter(mAdapter);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_choice_vote;
    }

    @Override
    protected void setListeners() {
        lvChoiceVote.setOnItemClickListener(this);
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mCallback.onActionDialogVote(mAdapter.getItem(_position).surveyId, mAdapter.getItem(_position).isActive);
    }
}