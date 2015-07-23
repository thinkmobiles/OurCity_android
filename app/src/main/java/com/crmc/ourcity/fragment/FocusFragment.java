package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.location.MyLocation;
import com.crmc.ourcity.model.LocationModel;
import com.crmc.ourcity.utils.ImageFilePath;
import com.crmc.ourcity.utils.IntentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class FocusFragment extends BaseFragment implements View.OnClickListener {

    private static final String PHOTO_FILE_EXTENSION = ".jpg";
    private String mPhotoFilePath;
    private ImageView ivPhoto;
    private EditText etNameStreet;
    private EditText etNameCity;

    public static FocusFragment newInstance() {
        return new FocusFragment();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPhoto = findView(R.id.ivPhoto_FF);
        etNameCity = findView(R.id.etCityName_FF);
        etNameStreet = findView(R.id.etNameStreet_FF);
        initCamera();
        getLocation();
        //initGallery();
    }



    /*private void initGallery() {
        btnGallery = (Button) findViewById(R.id.btnGallery);
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            btnGallery.setEnabled(false);
        } else {
            btnGallery.setOnClickListener(this);
        }
    }*/

    private void initCamera() {
//        btnCamera = (Button) findViewById(R.id.btnCamera);
//        if (!isCanGetCameraPicture()) {
//            btnCamera.setEnabled(false);
//        } else {
//            btnCamera.setOnClickListener(this);
//        }
        if (!isCanGetCameraPicture()) {
            ivPhoto.setEnabled(false);
        } else {
            ivPhoto.setOnClickListener(this);
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
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = timeStamp;
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
            Toast.makeText(getActivity(), "Can't create photo", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = IntentUtils.getGalleryStartIntent();
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, Constants.REQUEST_GALLERY_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PHOTO) {
            if (!TextUtils.isEmpty(mPhotoFilePath)) {
                File imageFile = new File(mPhotoFilePath);
                if (resultCode == Activity.RESULT_OK) {
                    if (imageFile.exists()) {
                        addPhotoToGallery(mPhotoFilePath);
                        ivPhoto.setImageURI(Uri.fromFile(imageFile));
                    } else {
                        Toast.makeText(getActivity(), "File i'nt found!", Toast.LENGTH_SHORT).show();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    imageFile.delete();
                }
            }
        } else if (requestCode == Constants.REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
            String mPhoto = ImageFilePath.getPath(getActivity(), data.getData());
            if (!TextUtils.isEmpty(mPhoto)) {
                File imageFile = new File(mPhoto);
                if (imageFile.exists()) {
                    ivPhoto.setImageURI(Uri.fromFile(imageFile));
                } else {
                    Toast.makeText(getActivity(), "File i'nt found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Uncompilable type!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addPhotoToGallery(String mPhotoFilePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mPhotoFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    public void getLocation() {
        LocationCallBack locationCallBack = new LocationCallBack() {
            @Override
            public void onSuccess(LocationModel modelLocation) {
                //Toast.makeText(getActivity(), modelLocation.nameCity + modelLocation.nameStreet, Toast
                // .LENGTH_SHORT).show();
                etNameCity.setText(modelLocation.nameCity);
                etNameStreet.setText(modelLocation.nameStreet);
            }

            @Override
            public void onFailure(boolean result) {
                if (!result) {
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", result + " result");
            }
        };
        new MyLocation(getActivity(), locationCallBack);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhoto_FF:
                openCamera();
                break;
        }
    }

//    @Override
//    protected int getContentView() {
//        return R.layout.fragment_focus;
//    }
//
//    @Override
//    public void onRetryClick() {
//
//    }
}
