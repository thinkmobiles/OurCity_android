package com.crmc.ourcity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.callback.LocationCallBack;
import com.crmc.ourcity.dialog.DialogActivity;
import com.crmc.ourcity.dialog.DialogType;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.loader.AddressLoader;
import com.crmc.ourcity.loader.SendTicketLoader;
import com.crmc.ourcity.loader.StreetsLoader;
import com.crmc.ourcity.location.CurrentLocation;
import com.crmc.ourcity.rest.request.appeals.CreateNewTicketWrapper;
import com.crmc.ourcity.rest.request.appeals.NewTicket;
import com.crmc.ourcity.rest.request.appeals.NewTicketObj;
import com.crmc.ourcity.rest.request.appeals.WSAddress;
import com.crmc.ourcity.rest.request.appeals.WSPhoneNumber;
import com.crmc.ourcity.rest.responce.address.AddressFull;
import com.crmc.ourcity.rest.responce.address.StreetsFull;
import com.crmc.ourcity.rest.responce.appeals.Location;
import com.crmc.ourcity.rest.responce.appeals.WSResult;
import com.crmc.ourcity.utils.Camera;
import com.crmc.ourcity.utils.EnumUtil;
import com.crmc.ourcity.utils.Gallery;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;
import com.crmc.ourcity.view.EditTextStreetAutoComplete;

import java.io.File;
import java.io.IOException;

/**
 * Created by SetKrul on 23.07.2015.
 */
public class AppealsFragment extends BaseFourStatesFragment implements OnClickListener, OnCheckedChangeListener {

    private String color;
    private String json;
    private String route;
    private CurrentLocation mLocation;
    private String mPhotoFilePath;
    private ImageView ivPhoto;
    private ImageView ivRotate;
    private LinearLayout llPhoto;
    private LinearLayout llAppeals;
    private EditTextStreetAutoComplete etNameStreet;
    private EditText etNameCity;
    private EditText etDescription;
    private FrameLayout flDescription;
    private EditText etNumberHouse;
    private Button btnSend;
    private Camera mCamera;
    private Gallery mGallery;
    private SwitchCompat swGpsOnOff;
    private String[] streets;

    public static AppealsFragment newInstance(String _colorItem, String _requestJson, String _requestRoute) {
        AppealsFragment mAppealsFragment = new AppealsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CONFIGURATION_KEY_COLOR, _colorItem);
        args.putString(Constants.CONFIGURATION_KEY_JSON, _requestJson);
        args.putString(Constants.CONFIGURATION_KEY_ROUTE, _requestRoute);
        mAppealsFragment.setArguments(args);
        return mAppealsFragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        color = getArguments().getString(Constants.CONFIGURATION_KEY_COLOR);
        json = getArguments().getString(Constants.CONFIGURATION_KEY_JSON);
        route = getArguments().getString(Constants.CONFIGURATION_KEY_ROUTE);
    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mCamera = new Camera(getActivity());
        mGallery = new Gallery(getActivity());
//        showContent();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mPhotoFilePath", mPhotoFilePath);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoFilePath = savedInstanceState.getString("mPhotoFilePath");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivPhoto = findView(R.id.ivPhoto_AF);
        ivRotate = findView(R.id.ivRotate_AF);
        etNameCity = findView(R.id.etCityName_AF);
        etNameStreet = findView(R.id.etStreetName_SUDF);
        swGpsOnOff = findView(R.id.swGpsOnOff_AF);
        etDescription = findView(R.id.etDescription_AF);
        flDescription = findView(R.id.flDescriptionContainer_AF);
        btnSend = findView(R.id.btnSend_AF);
        etNumberHouse = findView(R.id.etNumberHouse_AF);
        llPhoto = findView(R.id.llPhoto_AP);
        llAppeals = findView(R.id.llAppeals_AF);

        etNameCity.setText(getResources().getString(R.string.app_name));

        ivRotate.setEnabled(false);

        Image.init(Color.parseColor(color));
        llAppeals.setBackgroundColor(Image.lighterColor(0.2));
        ivPhoto.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.focus_camera, Image
                .darkenColor(0.2)));
        ivRotate.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.rotate,
                Image.darkenColor(0.2)));
        Image.setBackgroundColorArrayView(getActivity(), new View[]{etNameCity, etNameStreet, etNumberHouse,
                flDescription, llPhoto}, R.drawable.boarder_round_green_ff);
        Image.setBackgroundColorView(getActivity(), btnSend, R.drawable.selector_button_green_ff);
    }

    private LoaderManager.LoaderCallbacks<WSResult> mSendTicket = new LoaderManager.LoaderCallbacks<WSResult>() {

        @Override
        public Loader<WSResult> onCreateLoader(int id, Bundle args) {
            return new SendTicketLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<WSResult> loader, WSResult data) {
            showContent();
            if (data != null) {
                clearFields();
                Toast.makeText(getActivity(), "Ticket is sent", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<WSResult> loader) {

        }
    };

    private void clearFields() {
        etNameStreet.setText("");
        etNumberHouse.setText("");
        etDescription.setText("");
        ivPhoto.setImageDrawable(Image.setDrawableImageColor(getActivity(), R.drawable.focus_camera, Image
                .darkenColor(0.2)));
    }

    private LoaderManager.LoaderCallbacks<AddressFull> mAddressCallBack = new LoaderManager
            .LoaderCallbacks<AddressFull>() {

        @Override
        public Loader<AddressFull> onCreateLoader(int _id, Bundle _args) {
            return new AddressLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<AddressFull> _loader, AddressFull _data) {
            etNameCity.setText(_data.address.city);
            etNameStreet.setText(_data.address.street);
            etNameStreet.setError(null);
        }

        @Override
        public void onLoaderReset(Loader<AddressFull> _loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<StreetsFull> mStreetsCallBack = new LoaderManager
            .LoaderCallbacks<StreetsFull>() {

        @Override
        public Loader<StreetsFull> onCreateLoader(int _id, Bundle _args) {
            return new StreetsLoader(getActivity(), _args);
        }

        @Override
        public void onLoadFinished(Loader<StreetsFull> _loader, StreetsFull _data) {

            if (_data != null) {
                int numbersStreets = _data.streetsList.size();
                streets = new String[numbersStreets];
                for (int i = 0; i < numbersStreets; i++) {
                    streets[i] = _data.streetsList.get(i).streetName;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                        streets);
                etNameStreet.setAdapter(adapter);
            }
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<StreetsFull> _loader) {
        }
    };

    public void getAddress(double _lat, double _lon) {
        getActivity().getSupportLoaderManager().destroyLoader(Constants.LOADER_STREETS_ID);
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.BUNDLE_CONSTANTS_LAT, _lat);
        bundle.putDouble(Constants.BUNDLE_CONSTANTS_LON, _lon);
        if (getLoaderManager().getLoader(Constants.LOADER_ADDRESS_ID) == null) {
            getLoaderManager().initLoader(Constants.LOADER_ADDRESS_ID, bundle, mAddressCallBack);
        } else {
            getLoaderManager().restartLoader(Constants.LOADER_ADDRESS_ID, bundle, mAddressCallBack);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_JSON, json);
        bundle.putString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE, route);
        getLoaderManager().initLoader(Constants.LOADER_STREETS_ID, bundle, mStreetsCallBack);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        ivPhoto.setOnClickListener(this);
        ivRotate.setOnClickListener(this);
        swGpsOnOff.setOnCheckedChangeListener(this);
        btnSend.setOnClickListener(this);
        flDescription.setOnClickListener(this);
        etNameStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etNameStreet.getText().length() > 0) {
                    etNameStreet.setError(null);
                }
            }
        });

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

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Constants.REQUEST_TYPE_PHOTO:
                if (_data != null) {
                    if (_data.getIntExtra(Constants.REQUEST_INTENT_TYPE_PHOTO, 0) == Constants.REQUEST_PHOTO) {
                        mPhotoFilePath = mCamera.openCamera(this);
                    } else {
                        mGallery.openGallery(this);
                    }
                }
                break;

            case Constants.REQUEST_PHOTO:
                if (!TextUtils.isEmpty(mPhotoFilePath)) {
                    File imageFile = new File(mPhotoFilePath);
                    if (_resultCode == Activity.RESULT_OK) {
                        if (imageFile.exists()) {
                            mGallery.addPhotoToGallery(mPhotoFilePath);
                            try {
                                ivPhoto.setImageBitmap(Image.handleSamplingAndRotationBitmap(getActivity(), Uri
                                        .fromFile(imageFile), 400, 400));
                                ivRotate.setEnabled(true);
                            } catch (IOException e) {
                                ivRotate.setEnabled(false);
                                e.printStackTrace();
                            }
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
                        File imageFile1 = new File(mPhotoFilePath);
                        if (imageFile1.exists()) {
                            ivPhoto.setImageURI(Uri.fromFile(imageFile1));
                            ivRotate.setEnabled(true);
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

    @Override
    protected int getContentView() {
        return R.layout.fragment_appeals;
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.flDescriptionContainer_AF:
                etDescription.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.showSoftInput(etDescription, InputMethodManager.SHOW_IMPLICIT);
                break;
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
            case R.id.btnSend_AF:
                if (checkValidation()) {
                    showLoading("Sending ticket...");
                    NewTicket ticket = new NewTicket();
                    ticket.AttachedFiles = Image.convertBitmapToBase64(((BitmapDrawable) ivPhoto.getDrawable())
                            .getBitmap());
                    ticket.Description = etDescription.getText().toString();
                    Location location = new Location();
                    location.StreetName = etNameStreet.getText().toString();
                    location.HouseNumber = etNumberHouse.getText().toString();
                    ticket.Location = location;
                    CreateNewTicketWrapper ticketWrapper = new CreateNewTicketWrapper();
                    WSAddress wsAddress = new WSAddress();
                    WSPhoneNumber phoneNumber = new WSPhoneNumber();
                    ticket.ReporterAddress = wsAddress;
                    ticket.ReporterFaxNumber = phoneNumber;
                    ticket.ReporterHomePhoneNumber = phoneNumber;
                    ticket.ReporterMobilePhoneNumber = phoneNumber;
                    ticketWrapper.newTicket = ticket;
                    ticketWrapper.clientId = getResources().getInteger(R.integer.city_id);
                    ticketWrapper.ResidentId = SPManager.getInstance(getActivity()).getResidentId();
                    ticketWrapper.userName = SPManager.getInstance(getActivity()).getCRMCUsername();
                    ticketWrapper.password = SPManager.getInstance(getActivity()).getCRMCPassword();
                    NewTicketObj ticketObj = new NewTicketObj(ticketWrapper);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.BUNDLE_CONSTANT_PARCELABLE_TICKET, ticketObj);
                    getLoaderManager().initLoader(Constants.LOADER_SEND_APPEALS_ID, bundle, mSendTicket);
                    InputMethodManager imm2 = (InputMethodManager) getActivity().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm2.showSoftInput(btnSend, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                    popBackStack();
                }
                break;
        }
    }

    @Override
    public void onRetryClick() {
    }

    private boolean checkValidation() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etDescription.getText().toString())) {
            etDescription.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etNameStreet.getText().toString())) {
            etNameStreet.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }
        if (TextUtils.isEmpty(etNumberHouse.getText().toString())) {
            etNumberHouse.setError(getResources().getString(R.string.sign_up_dialog_error_text));
            isValid = false;
        }

        return isValid;
    }
}
