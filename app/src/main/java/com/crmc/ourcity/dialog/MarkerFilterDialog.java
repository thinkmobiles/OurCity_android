package com.crmc.ourcity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.MarkersListAdapter;
import com.crmc.ourcity.callback.CallBackWithData;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.MapMarker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MarkerFilterDialog extends BaseFourStatesFragment implements OnClickListener {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private TextView btnCancelFilter;
    private TextView btnSelectFilter;
    private ListView lvMarkerFilter;
    private CallBackWithData mCallback;
    private ArrayList<MapMarker> mMapMarkers;
    private MarkersListAdapter mAdapter;
    private WeakReference<DialogActivity> mActivity;

    public MarkerFilterDialog() {
        super();
    }

    public static MarkerFilterDialog newInstance(ArrayList<MapMarker> _mMapMarkers) {
        MarkerFilterDialog mDialogMapFilter = new MarkerFilterDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CONFIGURATION_KEY, _mMapMarkers);
        mDialogMapFilter.setArguments(args);
        return mDialogMapFilter;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mMapMarkers = getArguments().getParcelableArrayList(CONFIGURATION_KEY);
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        try {
            mActivity = new WeakReference<>((DialogActivity) _activity);
            mCallback = (CallBackWithData) _activity;
        } catch (ClassCastException _e) {
            throw new ClassCastException(_activity.toString() + " must implement OnActionDialogListenerWithData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        mActivity.clear();
    }

    @Override
    protected void initViews() {
        btnCancelFilter = findView(R.id.btnCancel_Filter_DMF);
        btnSelectFilter = findView(R.id.btnSelect_Filter_DMF);
        lvMarkerFilter = findView(R.id.lvMarkerFilter_DMF);
        mAdapter = new MarkersListAdapter(mActivity.get(), mMapMarkers);
        lvMarkerFilter.setAdapter(mAdapter);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dialog_marker_filter;
    }

    @Override
    protected void setListeners() {
        btnCancelFilter.setOnClickListener(this);
        btnSelectFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.btnCancel_Filter_DMF:
                mCallback.onActionDialogCancel(false);
                break;
            case R.id.btnSelect_Filter_DMF:
                mCallback.onActionDialogDataMarker(mAdapter.getResult());
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }
}