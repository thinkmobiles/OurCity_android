package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.EventsListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.ActionType;
import com.crmc.ourcity.model.EventsItemModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsFragment extends BaseFourStatesFragment implements OnItemClickListener {

    private ListView lvEvents;
    private EventsListAdapter mAdapter;
    private List<EventsItemModel> mEventsList = new ArrayList<>();
    private OnItemActionListener mOnItemActionListener;

    public static EventsFragment newInstance() {
        //noinspection deprecation
        return new EventsFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mOnItemActionListener = (OnItemActionListener) _activity;
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);

//        for (int i = 0; i < 10; i++) {
//            CatalogItemModel data = new CatalogItemModel("Some tvTitle " + i, ItemClickAction.FALSE);
//            mTestList.add(data);
//        }
        //if (mTestList.size()  0) {
        for (int i = 0; i < 10; i++) {
            EventsItemModel data = new EventsItemModel("Some tvTitle " + i, getDateTime(), "Some address", ActionType
                    .MAIL);
            mEventsList.add(data);
        }
        //}
        lvEvents = findView(R.id.lvEvents_EF);
        mAdapter = new EventsListAdapter(getActivity(), mEventsList);
        lvEvents.setAdapter(mAdapter);
        lvEvents.setOnItemClickListener(this);
        showContent();
    }

    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        mOnItemActionListener.onItemAction(mAdapter.getItem(_position));
    }

//    public interface OnItemActionListener {
//        void onItemAction(final CatalogItemModel catalogItemModel);
//    }

    public String getDateTime() {
        return new SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.ENGLISH).format(java.util.Calendar.getInstance()
                .getTime());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_catalog;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onDetach() {
        mOnItemActionListener = null;
        super.onDetach();
    }
}
