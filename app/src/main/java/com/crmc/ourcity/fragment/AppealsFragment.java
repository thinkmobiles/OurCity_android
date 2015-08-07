package com.crmc.ourcity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.AddressLoader;
import com.crmc.ourcity.location.CurrentLocation;
import com.crmc.ourcity.rest.responce.address.AddressFull;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.IntentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class AppealsFragment extends BaseFourStatesFragment implements OnClickListener, OnCheckedChangeListener,
        LoaderManager.LoaderCallbacks<AddressFull> {

    private CurrentLocation mLocation;
    private static final String PHOTO_FILE_EXTENSION = ".jpg";
    private String mPhotoFilePath;
    private ImageView ivPhoto;
    private EditText etNameStreet;
    private EditText etNameCity;

    private SwitchCompat swGpsOnOff;

    public static AppealsFragment newInstance() {
        return new AppealsFragment();
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
        etNameCity = findView(R.id.etCityName_AF);
        etNameStreet = findView(R.id.etNameStreet_AF);
        swGpsOnOff = findView(R.id.swGpsOnOff_AF);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivPhoto.setOnClickListener(this);
        swGpsOnOff.setOnCheckedChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocation != null) {
            mLocation.stopLocationUpdates();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton _buttonView, boolean _isChecked) {
        if (_isChecked) {
            getLocation();
        } else {
            mLocation.stopLocationUpdates();
            mLocation = null;
        }
    }

    private boolean isCanGetCameraPicture() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && takePictureIntent.resolveActivity(pm) != null;
    }

    private void openCamera() {
        final File imageFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        boolean isFolderExist = imageFolder.exists() || imageFolder.mkdir();
        if (isFolderExist) {
            @SuppressLint("SimpleDateFormat") String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format
                    (new Date());
            File imageFile;
            try {
                imageFile = File.createTempFile(imageFileName, PHOTO_FILE_EXTENSION, imageFolder);
            } catch (IOException e) {
                imageFile = null;
            }
            if (imageFile != null) {
                mPhotoFilePath = imageFile.getPath();
                Intent takePictureIntent = IntentUtils.getCameraStartIntent(Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, Constants.REQUEST_PHOTO);
            }

        } else {
            Toast.makeText(getActivity(), this.getResources().getString(R.string.cant_create_photo), Toast
                    .LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, Constants.REQUEST_GALLERY_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Constants.REQUEST_TYPE_PHOTO:
                if (_data != null) {
                    if (_data.getIntExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, 0) == Constants.REQUEST_PHOTO) {
                        if (isCanGetCameraPicture()) {
                            openCamera();
                        }
                    } else {
                        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
                        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            openGallery();
                        }
                    }
                }
                break;
            case Constants.REQUEST_PHOTO:
                if (!TextUtils.isEmpty(mPhotoFilePath)) {
                    File imageFile = new File(mPhotoFilePath);
                    if (_resultCode == Activity.RESULT_OK) {
                        if (imageFile.exists()) {
                            addPhotoToGallery(mPhotoFilePath);
                            ivPhoto.setImageURI(Uri.fromFile(imageFile));
                        } else {
                            Toast.makeText(getActivity(), this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (_resultCode == Activity.RESULT_CANCELED) {
                        imageFile.delete();
                    }
                }
                break;
            case Constants.REQUEST_GALLERY_IMAGE:
                if (_resultCode == Activity.RESULT_OK) {
                    mPhotoFilePath = Image.getPath(getActivity(), _data.getData());
                    if (!TextUtils.isEmpty(mPhotoFilePath)) {
                        File imageFile = new File(mPhotoFilePath);
                        if (imageFile.exists()) {
                            ivPhoto.setImageURI(Uri.fromFile(imageFile));
                        } else {
                            Toast.makeText(getActivity(), this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), this.getResources().getString(R.string.uncompilable_type),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void addPhotoToGallery(String _mPhotoFilePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(_mPhotoFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public void getLocation() {
        LocationCallBack locationCallBack = new LocationCallBack() {
            @Override
            public void onSuccess(double lat, double lon) {
                getAddress(lat, lon);
            }
            @Override
            public void onFailure(boolean result) {
                if (!result) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.uncompilable_type),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        mLocation = new CurrentLocation(getActivity(), locationCallBack);
    }

    public void getAddress(double _lat, double _lon) {
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.BUNDLE_CONSTANTS_LAT, _lat);
        bundle.putDouble(Constants.BUNDLE_CONSTANTS_LON, _lon);
        getLoaderManager().restartLoader(Constants.LOADER_ADDRESS_ID, bundle, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getLoaderManager().getLoader(Constants.LOADER_ADDRESS_ID) != null) {
            getLoaderManager().initLoader(Constants.LOADER_ADDRESS_ID, null, this);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_focus;
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.ivPhoto_AF:
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                EnumUtil.serialize(DialogType.class, DialogType.PHOTO).to(intent);
                startActivityForResult(intent, Constants.REQUEST_TYPE_PHOTO);
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }

    @Override
    public Loader<AddressFull> onCreateLoader(int _id, Bundle _args) {
        return new AddressLoader(getActivity(), _args);
    }

    @Override
    public void onLoadFinished(Loader<AddressFull> _loader, AddressFull _data) {
        etNameCity.setText(_data.address.city);
        etNameStreet.setText(_data.address.street);
    }

    @Override
    public void onLoaderReset(Loader<AddressFull> _loader) {
    }
}
