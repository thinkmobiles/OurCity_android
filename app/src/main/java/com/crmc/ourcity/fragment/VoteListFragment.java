package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.VotesListAdapter;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.VoteLoader;
import com.crmc.ourcity.rest.responce.vote.VoteFull;
import com.crmc.ourcity.utils.Image;

import java.util.List;

/**
 * Created by SetKrul on 07.10.2015.
 */
public class VoteListFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<List<VoteFull>> {

    private ListView lvVotes;
    private View vUnderLine_LLF;
    private String color;
    private String json;
    private String route;
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;

    private VotesListAdapter mAdapter;
    private OnListItemActionListener mOnListItemActionListener;

    public static VoteListFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String
            _title) {
        VoteListFragment mVoteListFragment = new VoteListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mVoteListFragment.setArguments(args);
        return mVoteListFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        title = getArguments().getString(Constants.NODE_TITLE);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mOnListItemActionListener = (OnListItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnListItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        super.onDetach();
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        loadLinkList();
    }

    @Override
    public void onLoadFinished(Loader<List<VoteFull>> _loader, List<VoteFull> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            if (_data.size() > 0) {
                mAdapter = new VotesListAdapter(getActivity(), _data);
                lvVotes.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else {
                showEmpty("לא בחרת");
            }
        } else {
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<VoteFull>> onCreateLoader(int _id, Bundle _args) {
        return new VoteLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<VoteFull>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout = findView(R.id.swipe_refresh_vote_list);
        lvVotes = findView(R.id.lvVotes_VLF);
        vUnderLine_LLF = findView(R.id.vUnderLine_VLF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_LLF.setBackgroundColor(Image.darkenColor(0.2));
        lvVotes.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvVotes.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvVotes.setOnItemClickListener(handleLinkItemClick());
        swipeRefreshLayout.setOnRefreshListener(this::loadLinkList);
        swipeInStart();
    }

    @NonNull
    private AdapterView.OnItemClickListener handleLinkItemClick() {
        return (parent, view, _position, id) -> mOnListItemActionListener.onVoteAction(mAdapter.getItem(_position));
    }

    public void swipeInStart() {
        TypedValue typed_value = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, typed_value, true);
        swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value
                .resourceId));
        if (!swipeRefreshLayout.isEnabled()) swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_vote_list;
    }

    @Override
    public void onRetryClick() {
        loadLinkList();
    }

    private void loadLinkList() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_LIST_LINK_ID, bundle, this);
    }
}
