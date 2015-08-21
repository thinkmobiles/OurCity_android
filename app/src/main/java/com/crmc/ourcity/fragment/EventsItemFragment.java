package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.Events;

/**
 * Created by SetKrul on 16.07.2015.
 */
public class EventsItemFragment extends BaseFourStatesFragment {

    private Events mEvents;
    private TextView tvTitle_EIF;
    private TextView tvDate_Text_EIF;
    private TextView tvDate_EIF;
    private TextView tvAddress_Text_EIF;
    private TextView tvAddress_EIF;
    private TextView tvObserv_Text_EIF;
    private TextView tvObserv_EIF;
    private TextView tvPrice_Text_EIF;
    private TextView tvPrice_EIF;
    private TextView tvRemarks_Text_EIF;
    private TextView tvRemarks_EIF;
    private TextView tvLink_EIF;

    private LinearLayout llDate_EIF;
    private LinearLayout llAddress_EIF;
    private LinearLayout llObserv_EIF;
    private LinearLayout llPrice_EIF;
    private LinearLayout llRemarks_EIF;

    public static EventsItemFragment newInstance(Events _events) {
        EventsItemFragment mEventsItemFragment = new EventsItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_EVENTS, _events);
        mEventsItemFragment.setArguments(args);
        return mEventsItemFragment;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mEvents = getArguments().getParcelable(Constants.CONFIGURATION_KEY_COLOR);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_events_item;
    }

    @Override
    public void onRetryClick() {}

    @Override
    public void onStop() {
        super.onStop();
    }

}
