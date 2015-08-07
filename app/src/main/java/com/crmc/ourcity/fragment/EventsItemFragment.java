package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.EventsItemModel;

/**
 * Created by SetKrul on 16.07.2015.
 */
public class EventsItemFragment extends BaseFourStatesFragment {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private EventsItemModel mEventsItemModel;

    public static EventsItemFragment newInstance(EventsItemModel _eventsItemModel) {
        EventsItemFragment eventsItemFragment = new EventsItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_KEY, _eventsItemModel);
        eventsItemFragment.setArguments(args);
        return eventsItemFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mEventsItemModel = getArguments().getParcelable(CONFIGURATION_KEY);
        Log.d("TAG", mEventsItemModel.title);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_catalog_item;
    }

    @Override
    public void onRetryClick() {}

    @Override
    public void onStop() {
        super.onStop();
    }

}
