package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.MapFilterListAdapter;
import com.crmc.ourcity.callback.MapFilterCallBack;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.MapFilterSelected;

import java.util.ArrayList;

/**
 * Created by SetKrul on 04.08.2015.
 */
public class DialogMarkerFilter extends BaseFourStatesFragment implements OnClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private TextView btnCancelFilter;
    private TextView btnSelectFilter;
    private ListView lvMapFilter;
    private MapFilterCallBack mCallback;
    private ArrayList<MapFilterSelected> mMapFilterSelected;
    private MapFilterListAdapter mAdapter;

    public DialogMarkerFilter() {
        super();
    }

    public static DialogMarkerFilter newInstance(ArrayList<MapFilterSelected> _mMapFilterSelected) {
        DialogMarkerFilter mDialogMapFilter = new DialogMarkerFilter();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONFIGURATION_KEY, _mMapFilterSelected);
        mDialogMapFilter.setArguments(args);
        return mDialogMapFilter;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mMapFilterSelected = getArguments().getParcelableArrayList(CONFIGURATION_KEY);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mCallback = (MapFilterCallBack) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListenerWithData");
        }
    }

    @Override
    protected void initViews() {
        btnCancelFilter = findView(R.id.btnCancel_Filter_MF);
        btnSelectFilter = findView(R.id.btnSelect_Filter_MF);
        lvMapFilter = findView(R.id.lvMapFilter);
        mAdapter = new MapFilterListAdapter(getActivity(), mMapFilterSelected);
        lvMapFilter.setAdapter(mAdapter);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_map_filter;
    }

    @Override
    protected void setListeners() {
        btnCancelFilter.setOnClickListener(this);
        btnSelectFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.btnCancel_Filter_MF:
                mCallback.onActionDialogDataCancel(false);
                break;
            case R.id.btnSelect_Filter_MF:
                mCallback.onActionDialogDataSelected(mAdapter.getResult());
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }
}