package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.CatalogListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.CatalogItemModel;
import com.crmc.ourcity.model.ActionType;
import com.crmc.ourcity.rest.responce.menu.MenuModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SetKrul on 15.07.2015.
 */
public class CatalogFragment extends BaseFourStatesFragment implements OnItemClickListener {

    private ListView mListView;
    private CatalogListAdapter mAdapter;
    private List<CatalogItemModel> mTestList = new ArrayList<>();
    private ListItemAction mListItemAction;

    public static CatalogFragment newInstance() {
        //noinspection deprecation
        return new CatalogFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListItemAction = (ListItemAction) activity;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        for (int i = 0; i < 10; i++) {
//            CatalogItemModel data = new CatalogItemModel("Some title " + i, ActionType.FALSE);
//            mTestList.add(data);
//        }
        //if (mTestList.size()  0) {
        for (int i = 0; i < 10; i++) {
            CatalogItemModel data = new CatalogItemModel("Some title " + i, getDateTime(), "Some address",
                    ActionType.MAIL);
            mTestList.add(data);
        }
        //}
        mListView = findView(R.id.lvTasks);
        mAdapter = new CatalogListAdapter(getActivity(), mTestList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        showContent();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListItemAction.onItemAction(mAdapter.getItem(position));
    }

//    public interface ListItemAction {
//        void onItemAction(final CatalogItemModel catalogItemModel);
//
//        void onMenuModelPrepared(List<MenuModel> _menuModel);
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
        mListItemAction = null;
        super.onDetach();
    }
}
