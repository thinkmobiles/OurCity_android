package com.crmc.ourcity.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.crmc.ourcity.R;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;

/**
 * Created by SetKrul on 03.09.2015.
 */
public class SendMailFragment extends BaseFourStatesFragment implements View.OnClickListener {

    private String color;

    public static SendMailFragment newInstance(String _colorItem) {
        SendMailFragment mSendMailFragment = new SendMailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        mSendMailFragment.setArguments(args);
        return mSendMailFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        showContent();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivPhoto = findView(R.id.ivPhoto_AF);
        ivRotate = findView(R.id.ivRotate_AF);
        etNameCity = findView(R.id.etCityName_AF);
        etNameStreet = findView(R.id.etStreetName_SUDF);
        swGpsOnOff = findView(R.id.swGpsOnOff_AF);
        etDescription = findView(R.id.etDescription_AF);
        btnSend = findView(R.id.btnSend_AF);
        etNumberHouse = findView(R.id.etNumberHouse_AF);
        llPhoto = findView(R.id.llPhoto_AP);
        llAppeals = findView(R.id.llAppeals_AF);

        etNameCity.setText(getResources().getString(R.string.app_name));

        Image.init(Color.parseColor(color));
        llAppeals.setBackgroundColor(Image.lighterColor(0.2));
        ivPhoto.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.focus_camera, Image
                .darkenColor(0.2)));
        ivRotate.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.rotate,
                Image.darkenColor(0.2)));
        Image.setBackgroundColorArrayView(getActivity(), new View[]{etNameCity, etNameStreet, etNumberHouse,
                etDescription, llPhoto}, R.drawable.boarder_round_green_ff);
        Image.setBackgroundColorView(getActivity(), btnSend, R.drawable.selector_button_green_ff);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivPhoto.setOnClickListener(this);
        ivRotate.setOnClickListener(this);
        swGpsOnOff.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_appeals;
    }


    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.ivPhoto_AF:
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.PHOTO).to(intent);
                startActivityForResult(intent, Constants.REQUEST_TYPE_PHOTO);
                break;
            case R.id.ivRotate_AF:
                if (!mPhotoFilePath.equals("")) {
                    Bitmap bitmap = Image.rotateImage(((BitmapDrawable) ivPhoto.getDrawable()).getBitmap(), 90);
                    ivPhoto.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    public void onRetryClick() {

    }
}
