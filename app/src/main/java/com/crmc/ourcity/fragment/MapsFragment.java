package com.crmc.ourcity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crmc.ourcity.R;
import com.crmc.ourcity.fourstatelayout.BaseFourStatesFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by SetKrul on 31.07.2015.
 */
public final class MapsFragment extends BaseFourStatesFragment implements OnMapReadyCallback {
    private SupportMapFragment mMap;
    CharSequence[] items;
    boolean[] itemsChecked;
    private Button btnPoint;

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public final void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            mMap.getMapAsync(this);
        }
        if (mMap != null) {
            getChildFragmentManager().findFragmentById(R.id.map);
            setUpMap();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();

    }

    private void setUpMap() {}

    @Override
    public final void onRetryClick() {}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        showContent();
        setCamera(googleMap);
        //setMarkers();
        //getInsertPoint(googleMap);
        //setTitel(googleMap);
        setbtn(googleMap);
    }

    private void setCamera(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(50.0, 50.0)).zoom(12).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }

    private void setbtn(final GoogleMap googleMap) {
        btnPoint = findView(R.id.map_btn_point);
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog_option(view, googleMap);
            }
        });
    }

//    private void setInterestPoint() {
//        if (!Common.getCategories().isEmpty()) {
//            items = new String[Common.getCategories().size()];
//            itemsChecked = new boolean[Common.getCategories().size()];
//            double lat = Double.parseDouble(Common.getCategories().get(0).getInterestPoint().get(0).getLat());
//            double lon = Double.parseDouble(Common.getCategories().get(0).getInterestPoint().get(0).getLon());
//            LatLng Chadera = new LatLng(lat, lon);
//            mMap.setMyLocationEnabled(true);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chadera, 12));
//
//            for (int i = 0; i < Common.getCategories().size(); i++) {
//                items[i] = Common.getCategories().get(i).getCategoryName();
//                itemsChecked[i] = true;
//            }
//        }
//    }

//    private void getInsertPoint() {
//        if (Common.getCategories().isEmpty()) {
//            new GetInterestPointToCityTask(this, Common.getOurCity(), this).execute();
//        } else {
//            setInterestPoint();
//        }
//    }

//    private void setTitel() {
//        if (Common.getTypeClient() == Common.TypeClient.machon) {
//            setTitleText(getResources().getString(R.string.titel_map_machon));
//
//        }
//    }

//    private void setMarkers() {
//        boolean isContainsBounds = false;
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        LatLng latLng;
//        List<InterestPoint> points = new ArrayList<InterestPoint>();
//
//        for (int i = 0; i < Common.getCategories().size(); i++) {
//            if (itemsChecked != null) {
//                if (itemsChecked[i]) {
//                    points.addAll(Common.getCategories().get(i).getInterestPoint());
//
//                    byte[] decodedString = Base64.decode(Common.getCategories().get(i).getIcon(), Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
//                    for (InterestPoint point : Common.getCategories().get(i).getInterestPoint()) {
//                        latLng = new LatLng(Double.parseDouble(point.getLat()), Double.parseDouble(point.getLon()));
//                        builder.include(latLng);
//                        isContainsBounds = true;
//                        mMap.addMarker(new MarkerOptions().title(point.getDescription()).position(latLng)).setIcon
//                                (BitmapDescriptorFactory.fromBitmap(decodedByte));
//                    }
//                }
//            }
//        }
//
//        if (isContainsBounds) mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 12));
//
//    }

    public void showDialog_option(View v, final GoogleMap googleMap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                googleMap.clear();
//                for (CharSequence c : items) {
//                    //setMarkers();
//                }
            }

        });
        builder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                itemsChecked[which] = isChecked;
            }
        });
        builder.show();
    }

//    @Override
//    public void onSuccess(Object o) {
//        setInterestPoint();
//    }
//
//    @Override
//    public void onFailure(ConnectionResultType errorType) {
//        DialogManager.showErrorDialog(this, errorType, null);
//    }
}
