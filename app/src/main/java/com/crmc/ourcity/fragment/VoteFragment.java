package com.crmc.ourcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.VoteGridAdapter;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.VoteLoader;
import com.crmc.ourcity.rest.responce.vote.VoteDetails;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.view.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 10.08.2015.
 */
public class VoteFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<VoteFull>> {
    private int cityNumber;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    VoteGridAdapter mAdapter;
    private Integer surveyId;
    List<VoteFull> mVoteFull;

    public static VoteFragment newInstance() {
        return new VoteFragment();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        cityNumber = 2;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, cityNumber);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_vote;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public Loader<List<VoteFull>> onCreateLoader(int _id, Bundle _args) {
        return new VoteLoader(getActivity(), _args);
    }

    @Override
    public void onLoadFinished(Loader<List<VoteFull>> _loader, List<VoteFull> _data) {
        if (surveyId == null) {
            Intent intent = new Intent(getActivity(), DialogActivity.class);
            EnumUtil.serialize(DialogType.class, DialogType.VOTE_CHOICE).to(intent);
            intent.putParcelableArrayListExtra(Constants.BUNDLE_VOTE, (ArrayList<? extends Parcelable>) _data);
            startActivityForResult(intent, Constants.REQUEST_VOTE);
            mVoteFull = _data;
        }
    }

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Constants.REQUEST_VOTE:
                if (_data != null) {
                    surveyId = _data.getIntExtra(Constants.BUNDLE_VOTE, 0);
                    showVote(surveyId);
                } else {
                    popBackStack();
                }
                break;
        }
    }

    private List<VoteDetails> getVote(Integer _surveyId) {
        for (int i = 0; i < mVoteFull.size(); i++) {
            if (_surveyId.equals(mVoteFull.get(i).surveyId)) {
                return mVoteFull.get(i).optionsList;
            }
        }
        return new ArrayList<>();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = findView(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void showVote(Integer _surveyId) {
        mAdapter = new VoteGridAdapter(getVote(_surveyId));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(),
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(Context _context, View _view, int _position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
                VoteDetails voteDetails = mAdapter.getItem(_position);
                if (voteDetails.surveyId != null) {

                }
            }
        }));
        showContent();
    }

    @Override
    public void onLoaderReset(Loader<List<VoteFull>> _loader) {
    }
}
