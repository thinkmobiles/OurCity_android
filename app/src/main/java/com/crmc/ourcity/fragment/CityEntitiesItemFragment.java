package com.crmc.ourcity.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.responce.events.CityEntities;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

/**
 * Created by SetKrul on 31.08.2015.
 */
public class CityEntitiesItemFragment extends BaseFourStatesFragment {

    private CityEntities mCityEntities;
    private TextView tvEntityName_CEIF;
    private TextView tvDetails_Text_CEIF;
    private TextView tvEmail_Text_CEIF;
    private TextView tvInformation_Text_CEIF;
    private TextView tvPhone_Text_CEIF;
    private TextView tvPost_Text_CEIF;
    private TextView tvMobile_Text_CEIF;

    private View vUnderLine_CEIF;
    private View vBottomLine_CEIF;

    private LinearLayout llDetails_CEIF;
    private LinearLayout llEmail_CEIF;
    private LinearLayout llInformation_CEIF;
    private LinearLayout llPhone_CEIF;
    private LinearLayout llPost_CEIF;
    private LinearLayout llMobile_CEIF;

    private ImageView ivCall_CEIF;
    private ImageView ivSendMail_CEIF;
    private ImageView ivCall_Mobile_CEIF;

    public static CityEntitiesItemFragment newInstance(CityEntities _cityEntities) {
        CityEntitiesItemFragment mCityEntitiesItemFragment = new CityEntitiesItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CONFIGURATION_KEY_CITY_ENINIES, _cityEntities);
        mCityEntitiesItemFragment.setArguments(args);
        return mCityEntitiesItemFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mCityEntities = getArguments().getParcelable(Constants.CONFIGURATION_KEY_CITY_ENINIES);
    }

    @Override
    public void onResume() {
        configureActionBar(true, true, mCityEntities.entityName);
        super.onResume();
    }

    @Override
    protected void initViews() {
        super.initViews();

        tvEntityName_CEIF = findView(R.id.tvEntityName_CEIF);
        tvDetails_Text_CEIF = findView(R.id.tvDetails_Text_CEIF);
        tvEmail_Text_CEIF = findView(R.id.tvEmail_Text_CEIF);
        tvInformation_Text_CEIF = findView(R.id.tvInformation_Text_CEIF);
        tvPhone_Text_CEIF = findView(R.id.tvPhone_Text_CEIF);
        tvPost_Text_CEIF = findView(R.id.tvPost_Text_CEIF);
        tvMobile_Text_CEIF = findView(R.id.tvMobile_Text_CEIF);

        vUnderLine_CEIF = findView(R.id.vUnderLine_CEIF);
        vBottomLine_CEIF = findView(R.id.vBottomLine_CEIF);

        llDetails_CEIF = findView(R.id.llDetails_CEIF);
        llEmail_CEIF = findView(R.id.llEmail_CEIF);
        llInformation_CEIF = findView(R.id.llInformation_CEIF);
        llPhone_CEIF = findView(R.id.llPhone_CEIF);
        llPost_CEIF = findView(R.id.llPost_CEIF);
        llMobile_CEIF = findView(R.id.llMobile_CEIF);

        ivCall_CEIF = findView(R.id.ivCall_CEIF);
        ivSendMail_CEIF = findView(R.id.ivSendMail_CEIF);
        ivCall_Mobile_CEIF = findView(R.id.ivCall_Mobile_CEIF);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        checkData(mCityEntities.entityName, tvEntityName_CEIF, tvEntityName_CEIF);
        checkData(mCityEntities.details, tvDetails_Text_CEIF, llDetails_CEIF);
        checkData(mCityEntities.emailAddress, tvEmail_Text_CEIF, llEmail_CEIF);
        checkData(mCityEntities.information, tvInformation_Text_CEIF, llInformation_CEIF);
        checkData(mCityEntities.phoneNumber, tvPhone_Text_CEIF, llPhone_CEIF);
        checkData(mCityEntities.entityPost, tvPost_Text_CEIF, llPost_CEIF);
        checkData(mCityEntities.mobileNumber, tvMobile_Text_CEIF, llMobile_CEIF);
        setImage();
        showContent();
    }

//    private void checkData(String _text, TextView _tvView, View _view){
//        if (!TextUtils.isEmpty(_text)){
//            _tvView.setText(_text);
//        } else {
//            _view.setVisibility(View.GONE);
//        }
//    }

    private void setImage(){
        if (!TextUtils.isEmpty(mCityEntities.phoneNumber)){
            ivCall_CEIF.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.phone2, Image
                    .darkenColor(0.2)));
        } else {
            ivCall_CEIF.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mCityEntities.emailAddress)){
            ivSendMail_CEIF.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.mail, Image
                    .darkenColor(0.2)));
        } else {
            ivSendMail_CEIF.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mCityEntities.mobileNumber)){
            ivCall_Mobile_CEIF.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.phone, Image
                    .darkenColor(0.2)));
        } else {
            ivCall_Mobile_CEIF.setVisibility(View.GONE);
        }
        vUnderLine_CEIF.setBackgroundColor(Image.darkenColor(0.2));
        vBottomLine_CEIF.setBackgroundColor(Image.darkenColor(0.2));
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivCall_CEIF.setOnClickListener(handleClick());
        ivSendMail_CEIF.setOnClickListener(handleClick());
        ivCall_Mobile_CEIF.setOnClickListener(handleClick());
    }

    @NonNull
    private View.OnClickListener handleClick() {
        return v -> {
            switch (v.getId()) {
                case R.id.ivCall_CEIF:
                    try {
                        startActivityForResult(Intent.createChooser(IntentUtils.getIntentCall(mCityEntities
                                .phoneNumber), getResources().getString(R.string.call_hint)), 58);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.app_no_call_client), Toast
                                .LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivSendMail_CEIF:
                    try {
                        startActivity(Intent.createChooser(IntentUtils.getIntentMail(mCityEntities.emailAddress), getResources().getString(R.string.send_mail_hint)));
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.app_no_mail_client), Toast
                                .LENGTH_SHORT).show();
                    }
                    break;
                case R.id.ivCall_Mobile_CEIF:
                    try {
                        startActivityForResult(Intent.createChooser(IntentUtils.getIntentCall(mCityEntities
                                .mobileNumber), getResources().getString(R.string.call_hint)), 59);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.app_no_call_client), Toast
                                .LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_city_entities_item;
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
