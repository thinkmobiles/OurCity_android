package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.CatalogListAdapter;
import com.crmc.ourcity.model.CatalogItemModel;
import com.crmc.ourcity.model.ItemClickAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * Created by SetKrul on 15.07.2015.
 */
public class  CatalogTestFragment  extends BaseFragment{

    private ListView mListView;
    private CatalogListAdapter mAdapter;
    private List mTestList = new ArrayList<>();

    public CatalogTestFragment() {

    }

    public static CatalogTestFragment newInstance() {
        //noinspection deprecation
        return new CatalogTestFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        for (int i = 0; i < 10; i++) {
//            CatalogItemModel data = new CatalogItemModel("Some title " + i, ItemClickAction.FALSE);
//            mTestList.add(data);
//        }
        for (int i = 0; i < 10; i++) {
            CatalogItemModel data = new CatalogItemModel("Some title " + i, getDateTime(), "Some address", ItemClickAction
                    .FALSE);
            mTestList.add(data);
        }

        mListView = findView(R.id.lvTasks);
        mAdapter = new CatalogListAdapter(getActivity(), mTestList);
        mListView.setAdapter(mAdapter);
    }

    public String getDateTime() {
        return new java.text.SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.ENGLISH).format(java.util.Calendar
                .getInstance().getTime());
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.catalog_fragment;
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}
