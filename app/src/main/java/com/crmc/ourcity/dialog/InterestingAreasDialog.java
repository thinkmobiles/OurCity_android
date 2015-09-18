package com.crmc.ourcity.dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.InterestingAreasAdapter;
import com.crmc.ourcity.fragment.BaseFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.InterestingAreaLoader;
import com.crmc.ourcity.loader.SendingAreasLoader;
import com.crmc.ourcity.rest.responce.interestAreas.FullInterestArea;
import com.crmc.ourcity.rest.responce.interestAreas.InterestingArea;
import com.crmc.ourcity.rest.responce.interestAreas.ResidentResponse;
import com.crmc.ourcity.utils.SPManager;

import java.util.ArrayList;
import java.util.List;

import static com.crmc.ourcity.global.Constants.LOADER_INTERESTING_AREAS_ID;
import static com.crmc.ourcity.global.Constants.LOADER_SEND_INTERESTING_AREAS_ID;



/**
 * Created by andrei on 17.09.15.
 */
public class InterestingAreasDialog extends BaseFragment implements LoaderManager.LoaderCallbacks {
    private View root;
    private ListView mLvInterestiongAreas;
    private Button mBtnSave;
    private ResidentResponse mResidentResponse;
    private InterestingAreasAdapter mAdapter;
    private ProgressDialog dialogLoading;
//
//    @Override
//    protected void initViews() {
//        super.initViews();
//        mLvInterestiongAreas = findView(R.id.lvInterestingAreas_DIA);
//        mBtnSave = findView(R.id.btnSave_DIA);
//        mAdapter = new InterestingAreasAdapter(getActivity(), mInterestingAreas);
//        mLvInterestiongAreas.setAdapter(mAdapter);
//        showContent();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dialog_interest_areas, container, false);
        findUI(root);
        setListeners();
        dialogLoading = new ProgressDialog(getActivity());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, getResources().getInteger(R.integer.city_id));
        bundle.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, SPManager.getInstance(getActivity()).getResidentId());
        getLoaderManager().restartLoader(LOADER_INTERESTING_AREAS_ID, bundle, this);
    }

    private void findUI(View root) {
        mLvInterestiongAreas = (ListView) root.findViewById(R.id.lvInterestingAreas_DIA);
        mBtnSave = (Button) root.findViewById(R.id.btnSave_DIA);
    }

    private void setListeners() {
        mBtnSave.setOnClickListener(sendSelectedInterestingAreasToServer());
    }

    @NonNull
    private View.OnClickListener sendSelectedInterestingAreasToServer() {
        return v -> {

            List<InterestingArea> selectedInterestingAreas = Stream.of(mResidentResponse.interestAreasModelsBool)
                    .filter(item -> item.Value)
                    .map(item -> item.Key)
                    .collect(Collectors.toList());

            Bundle args = new Bundle();
            args.putInt(Constants.BUNDLE_CONSTANT_RESIDENT_ID, SPManager.getInstance(getActivity()).getResidentId());
            args.putInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER, getResources().getInteger(R.integer.city_id));
            args.putParcelableArrayList(Constants.BUNDLE_SELECTED_AREAS, (ArrayList<? extends Parcelable>) selectedInterestingAreas);

            getLoaderManager().restartLoader(LOADER_SEND_INTERESTING_AREAS_ID, args, this);
        };
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        dialogLoading.setMessage(getResources().getString(R.string.loading_string_sign_up_dialog));
        dialogLoading.show();

        switch (id) {
            case LOADER_INTERESTING_AREAS_ID:
                loader = new InterestingAreaLoader(getActivity(), args);
                break;
            case LOADER_SEND_INTERESTING_AREAS_ID:
                loader = new SendingAreasLoader(getActivity(), args);
                break;

        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (dialogLoading.isShowing())
            dialogLoading.dismiss();
        switch (loader.getId()) {
            case LOADER_INTERESTING_AREAS_ID:
                mResidentResponse = (ResidentResponse) data;
                mAdapter = new InterestingAreasAdapter(getActivity(), mResidentResponse);
                mLvInterestiongAreas.setAdapter(mAdapter);
                break;
            case LOADER_SEND_INTERESTING_AREAS_ID:
                boolean response = (Boolean) data;
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
