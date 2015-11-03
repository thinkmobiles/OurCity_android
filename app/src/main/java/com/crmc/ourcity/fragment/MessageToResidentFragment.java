package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.MassageToResidentAdapter;
import com.crmc.ourcity.callback.OnItemActionListener;
import com.crmc.ourcity.callback.OnListItemActionListener;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.MassageToResidentLoader;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.utils.Image;

import java.util.List;

public class MessageToResidentFragment extends BaseFourStatesFragment implements LoaderManager
        .LoaderCallbacks<List<MassageToResident>> {

    private ListView lvMassageToResident;
    private View vUnderLine_MTRF;
    private String color;
    private String json;
    private String route;
    private String title;
    private boolean isForPush;
    private String pushMessage, pushLink;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnItemActionListener mItemActionListener;
    private OnListItemActionListener mOnListItemActionListener;

    private MassageToResidentAdapter mAdapter;

    public static MessageToResidentFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title) {
        MessageToResidentFragment mMessageToResidentFragment = new MessageToResidentFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        mMessageToResidentFragment.setArguments(args);
        return mMessageToResidentFragment;
    }

    public static MessageToResidentFragment newInstance(String _colorItem, String _requestJson, String _requestRoute, String _title, boolean  _isForPush,
                                                        String _pushMessage, String _pushLink) {
        MessageToResidentFragment mMessageToResidentFragment = new MessageToResidentFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.CONFIGURATION_KEY_FOR_PUSH, _isForPush);
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        args.putString(Constants.NODE_TITLE, _title);
        args.putString(Constants.CONFIGURATION_KEY_LINK, _pushLink);
        args.putString(Constants.CONFIGURATION_KEY_MESSAGE, _pushMessage);
        mMessageToResidentFragment.setArguments(args);
        return mMessageToResidentFragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mOnListItemActionListener = (OnListItemActionListener) _activity;
            mItemActionListener = (OnItemActionListener) _activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(_activity.toString() + " must implement OnItemActionListener");
        }
    }

    @Override
    public void onDetach() {
        mOnListItemActionListener = null;
        mItemActionListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        isForPush = getArguments().getBoolean(Constants.CONFIGURATION_KEY_FOR_PUSH, false);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
        title = getArguments().getString(Constants.NODE_TITLE);
        pushMessage = getArguments().getString(Constants.CONFIGURATION_KEY_MESSAGE);
        pushLink = getArguments().getString(Constants.CONFIGURATION_KEY_LINK);
        if (isForPush) mItemActionListener.onMessageToResidentDetailTransition(pushMessage, pushLink);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, title);
        super.onResume();
        loadMessagesToResident();
    }

    @Override
    public void onLoadFinished(Loader<List<MassageToResident>> _loader, List<MassageToResident> _data) {
        swipeRefreshLayout.setRefreshing(false);
        if (_data != null) {
            if (_data.size() > 0) {
                mAdapter = new MassageToResidentAdapter(getActivity(), _data);
                lvMassageToResident.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                showContent();
            } else {
                showEmpty(getResources().getString(R.string.no_messages_for_resident));
            }
        } else {
//            showEmpty(getResources().getString(R.string.no_messages_for_resident));
            showError(getResources().getString(R.string.connection_error));
        }
    }

    @Override
    public Loader<List<MassageToResident>> onCreateLoader(int _id, Bundle _args) {
        return new MassageToResidentLoader(getActivity(), _args);
    }

    @Override
    public void onLoaderReset(Loader<List<MassageToResident>> _loader) {
    }

    @Override
    protected void initViews() {
        super.initViews();
        //((AppCompatActivity)getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = findView(R.id.swipe_refresh_message_to_resident);
        lvMassageToResident = findView(R.id.lvMassageToResident_MTRF);
        vUnderLine_MTRF = findView(R.id.vUnderLine_MTRF);
        try {
            Image.init(Color.parseColor(color));
        } catch (Exception e) {
            Image.init(Color.BLACK);
        }
        vUnderLine_MTRF.setBackgroundColor(Image.darkenColor(0.2));
        lvMassageToResident.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvMassageToResident.setDividerHeight(4);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        lvMassageToResident.setOnItemClickListener((parent, view, position, id) -> {
            mOnListItemActionListener.onMessageItenAction(mAdapter.getItem(position));
        });
        swipeRefreshLayout.setOnRefreshListener(this::loadMessagesToResident);
        swipeInStart();
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
        return R.layout.fragment_message_to_resident;
    }

    @Override
    public void onRetryClick() {
        loadMessagesToResident();
    }

    private void loadMessagesToResident() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().restartLoader(Constants.LOADER_MESSAGE_TO_RESIDENT, bundle, this);
    }
}
