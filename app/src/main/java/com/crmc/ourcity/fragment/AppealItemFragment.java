package com.crmc.ourcity.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.appeals.ResultObject;
import com.crmc.ourcity.utils.Image;

/**
 * Created by andrei on 04.10.15.
 */
public class AppealItemFragment extends BaseFourStatesFragment {

    private ResultObject resultObject;
    private TextView tvDescription, tvAddress, tvDate;
    private ImageView ivDetailPhoto;
    private View vUnderLine_AIF, vBottomLine_AIF;
    private LinearLayout llAddress, llDescription, llDate;


    public static AppealItemFragment newInstance(ResultObject _resultObject) {
        AppealItemFragment frgmt = new AppealItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_APPEAL_ITEM, _resultObject);
        frgmt.setArguments(args);
        return frgmt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultObject = getArguments().getParcelable(Constants.CONFIGURATION_KEY_APPEAL_ITEM);
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvDescription = findView(R.id.tvDescription_Text_AIF);
        tvAddress = findView(R.id.tvAddress_Text_AIF);
        tvDate = findView(R.id.tvDate_Text_AIF);
        ivDetailPhoto = findView(R.id.ivPhoto_AIF);

        llAddress = findView(R.id.llAddress_AIF);
        llDescription = findView(R.id.llDescription_AIF);
        llDate = findView(R.id.llDate_AIF);

        vUnderLine_AIF = findView(R.id.vUnderLine_AIF);
        vBottomLine_AIF = findView(R.id.vBottomLine_AIF);
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        checkData(resultObject.Description, tvDescription, llDescription);
        checkData(resultObject.DateCreatedClient, tvDate, llDate);
        checkData(resultObject.Location.HouseNumber.trim() + " " + resultObject.Location.StreetName.trim(),
                tvAddress, llAddress);
        if(!TextUtils.isEmpty(resultObject.AttachedFiles))
            ivDetailPhoto.setImageBitmap(Image.convertBase64ToBitmap(resultObject.AttachedFiles));
        setImage();
        showContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureActionBar(true, true, resultObject.ReferenceID);
    }

    private void setImage(){
        vUnderLine_AIF.setBackgroundColor(Image.darkenColor(0.2));
        vBottomLine_AIF.setBackgroundColor(Image.darkenColor(0.2));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_appeal_item;
    }

    @Override
    public void onRetryClick() {

    }
}
