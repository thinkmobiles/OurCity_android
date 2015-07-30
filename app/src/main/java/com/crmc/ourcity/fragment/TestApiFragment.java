package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.VoteReplyLoader;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class TestApiFragment extends BaseFourStatesFragment implements LoaderManager.LoaderCallbacks<String> {
    private int selectedOptionId;
    private int age;
    private int gender;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedOptionId = 1;
        age = 27;
        gender = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_CONSTANT_SELECTED_OPTION_ID, selectedOptionId);
        bundle.putInt(Constants.BUNDLE_CONSTANT_AGE, age);
        bundle.putInt(Constants.BUNDLE_CONSTANT_GENDER, gender);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new VoteReplyLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
//        for (int i = 0; i < data.size(); i++) {
//            Log.d("TAG", "TAG: " + data.get(i).surveyTitle + " and " + data.get(0).optionsList.get(0)
//                    .optionDescription);
//        }
        Log.d("TAG", "TAG: " + data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
}
