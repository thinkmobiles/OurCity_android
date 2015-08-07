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
import com.crmc.ourcity.model.CatalogItemModel;
import com.crmc.ourcity.model.ItemClickAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class EventsFragment extends BaseFourStatesFragment implements OnItemClickListener {

    private ListView mListView;
    private EventsListAdapter mAdapter;
    private List<CatalogItemModel> mTestList = new ArrayList<>();
    private OnItemActionListener mOnItemActionListener;

    public static EventsFragment newInstance() {
        //noinspection deprecation
        return new EventsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnItemActionListener = (OnItemActionListener) activity;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        for (int i = 0; i < 10; i++) {
//            CatalogItemModel data = new CatalogItemModel("Some tvTitle " + i, ItemClickAction.FALSE);
//            mTestList.add(data);
//        }
        //if (mTestList.size()  0) {
        for (int i = 0; i < 10; i++) {
            CatalogItemModel data = new CatalogItemModel("Some tvTitle " + i, getDateTime(), "Some address",
                    ItemClickAction.MAIL);
            mTestList.add(data);
        }
        //}
        mListView = findView(R.id.lvTasks);
        mAdapter = new EventsListAdapter(getActivity(), mTestList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        showContent();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnItemActionListener.onItemAction(mAdapter.getItem(position));
    }

//    public interface OnItemActionListener {
//        void onItemAction(final CatalogItemModel catalogItemModel);
//    }

    public String getDateTime() {
        return new SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.ENGLISH).format(java.util.Calendar.getInstance().getTime());
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
