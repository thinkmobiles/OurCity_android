package com.crmc.ourcity.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.adapter.PhonesListAdapter;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.utils.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SetKrul on 05.08.2015.
 */
public class PhonesFragment extends BaseFourStatesFragment {

    private ListView lvPhones;
    private List<Phones> mPhonesList;
    private PhonesListAdapter mAdapter;
    private View vUnderLine_FP;

    public static PhonesFragment newInstance(List<Phones> _phonesList) {
        PhonesFragment mPhonesFragment = new PhonesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.CONFIGURATION_KEY_PHONE_LIST, (ArrayList<? extends Parcelable>)
                _phonesList);
        mPhonesFragment.setArguments(args);
        return mPhonesFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mPhonesList = getArguments().getParcelableArrayList(Constants.CONFIGURATION_KEY_PHONE_LIST);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initViews() {
        super.initViews();
       // ((AppCompatActivity) getActivity()).getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configureActionBar(true, true);
        lvPhones = findView(R.id.lvPhones_FP);
        vUnderLine_FP = findView(R.id.vUnderLine_FP);
        vUnderLine_FP.setBackgroundColor(Image.darkenColor(0.2));
        lvPhones.setDivider(new ColorDrawable(Image.darkenColor(0.2)));
        lvPhones.setDividerHeight(4);
        mAdapter = new PhonesListAdapter(getActivity(), mPhonesList);
        lvPhones.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_phones;
    }

    @Override
    public void onRetryClick() {
    }
}
