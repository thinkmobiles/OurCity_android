package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.google.android.gms.maps.GoogleMap;


/**
 *
 * Created by klim on 06.07.15.
 */
public final class MapsFragment extends BaseFourStatesFragment implements View.OnClickListener {
    private GoogleMap mMap;
    private View mView;

    public static MapsFragment newInstance() {
        //noinspection deprecation
        return new MapsFragment();
    }

    @Override
    protected final int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
//        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//        showContent();
    }


    @Override
    public final void onDestroyView() {
        super.onDestroyView();
//        MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        if (f != null)
//            getFragmentManager().beginTransaction().remove(f).commit();
    }



    @Override
    public final void onClick(final View _view) {
    }

    @Override
    public final void onRetryClick() {

    }

}