package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.model.CatalogItemModel;

/**
 * Created by SetKrul on 16.07.2015.
 */
public class CatalogItemFragment extends BaseFourStatesFragment {

    private static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";
    private CatalogItemModel catalogItemModel;

    public static CatalogItemFragment newInstance(CatalogItemModel catalogItemModel) {
        CatalogItemFragment catalogItemFragment = new CatalogItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_KEY, catalogItemModel);
        catalogItemFragment.setArguments(args);
        return catalogItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catalogItemModel = getArguments().getParcelable(CONFIGURATION_KEY);
        Log.d("TAG", catalogItemModel.title);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_catalog_item;
    }

    @Override
    public void onRetryClick() {}

    @Override
    public void onStop() {
        super.onStop();
    }

}
